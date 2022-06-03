package com.hollingsworth.arsnouveau.client.renderer.entity;

import com.hollingsworth.arsnouveau.ArsNouveau;
import com.hollingsworth.arsnouveau.api.client.CosmeticRenderUtil;
import com.hollingsworth.arsnouveau.api.item.ICosmeticItem;
import com.hollingsworth.arsnouveau.common.entity.familiar.FamiliarStarbuncle;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import javax.annotation.Nullable;

public class FamiliarCarbyRenderer  extends GeoEntityRenderer<FamiliarStarbuncle> {

    private FamiliarStarbuncle familiar;
    private MultiBufferSource buffer;

    public FamiliarCarbyRenderer(EntityRendererProvider.Context manager) {
        super(manager, new FamiliarCarbyModel());
    }

    @Override
    protected void applyRotations(FamiliarStarbuncle entityLiving, PoseStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
    }

    @Override
    public void render(FamiliarStarbuncle entity, float entityYaw, float p_225623_3_, PoseStack matrixStack, MultiBufferSource iRenderTypeBuffer, int p_225623_6_) {
        super.render(entity, entityYaw, p_225623_3_, matrixStack, iRenderTypeBuffer, p_225623_6_);
    }

    @Override
    public void renderEarly(FamiliarStarbuncle animatable, PoseStack stackIn, float ticks, MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) {
        this.familiar = animatable;
        this.buffer = renderTypeBuffer;
        super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks);
    }

    @Override
    public void renderRecursively(GeoBone bone, PoseStack stack, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {

        if (this.familiar.getCosmeticItem().getItem() instanceof ICosmeticItem cosmetic && cosmetic.getBone().equals(bone.getName())){
            CosmeticRenderUtil.renderCosmetic(bone, stack, this.buffer, familiar, packedLightIn);
            bufferIn = buffer.getBuffer(RenderType.entityCutoutNoCull(getColor(familiar)));
        }

        super.renderRecursively(bone, stack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    public ResourceLocation getColor(FamiliarStarbuncle e){
        String color = e.getColor().toLowerCase();

        if (color.isEmpty()) color = "orange";

        return new ResourceLocation(ArsNouveau.MODID, "textures/entity/carbuncle_" + color +".png");
    }

    @Override
    public ResourceLocation getTextureLocation(FamiliarStarbuncle entity) {
        return getColor(entity);
    }

    @Override
    public RenderType getRenderType(FamiliarStarbuncle animatable, float partialTicks, PoseStack stack, @Nullable MultiBufferSource renderTypeBuffer, @Nullable VertexConsumer vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.entityCutoutNoCull(textureLocation);
    }

    public static class FamiliarCarbyModel extends AnimatedGeoModel<FamiliarStarbuncle> {

        private final ResourceLocation WILD_TEXTURE = new ResourceLocation(ArsNouveau.MODID, "textures/entity/carbuncle_wild_orange.png");
        private final ResourceLocation TAMED_TEXTURE = new ResourceLocation(ArsNouveau.MODID, "textures/entity/carbuncle_orange.png");

        @Override
        public void setLivingAnimations(FamiliarStarbuncle entity, Integer uniqueID, @Nullable AnimationEvent customPredicate) {
            super.setLivingAnimations(entity, uniqueID, customPredicate);
            IBone head = this.getAnimationProcessor().getBone("head");
            EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
            head.setRotationX(extraData.headPitch * 0.017453292F);
            head.setRotationY(extraData.netHeadYaw * 0.017453292F);
        }

        @Override
        public ResourceLocation getModelLocation(FamiliarStarbuncle carbuncle) {
            return new ResourceLocation(ArsNouveau.MODID , "geo/carbuncle.geo.json");
        }

        @Override
        public ResourceLocation getTextureLocation(FamiliarStarbuncle carbuncle) {
            return WILD_TEXTURE;
        }

        @Override
        public ResourceLocation getAnimationFileLocation(FamiliarStarbuncle carbuncle) {
            return new ResourceLocation(ArsNouveau.MODID , "animations/starbuncle_animations.json");
        }

    }
}