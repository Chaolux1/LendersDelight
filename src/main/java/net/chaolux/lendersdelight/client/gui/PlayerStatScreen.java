package net.chaolux.lendersdelight.client.gui;

import com.mojang.blaze3d.platform.InputConstants;
import net.chaolux.lendersdelight.ServerConfig;
import net.chaolux.lendersdelight.client.ClientKeyBind;
import net.chaolux.lendersdelight.client.ClientStat;
import net.chaolux.lendersdelight.client.ClientStatTooltipHelper;
import net.chaolux.lendersdelight.coomon.stat.*;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.lwjgl.glfw.GLFW;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class PlayerStatScreen extends Screen {
    private static final ResourceLocation STAT_SCREEN=ResourceLocation.fromNamespaceAndPath("lendersdelight","textures/gui/stat/screen.png");
    private static final ResourceLocation STAT_SPRITE=ResourceLocation.fromNamespaceAndPath("lendersdelight","textures/gui/stat/sprite.png");
    private static final ResourceLocation STAT_ICON=ResourceLocation.fromNamespaceAndPath("lendersdelight","textures/gui/stat/icon.png");
    private static final StatType[] STAT_TYPES=StatType.values();
    private static final int SCREEN=256;
    private static final int ROW=6;
    private static final int ROW_HEIGHT=27;
    private static final int ROW_WIDTH=224;
    private static final int ROW_TOP=52;
    private static final int ROW_LEFT=16;
    private static final int ROW_SIZE=28;
    private static final int OFFSET=30;
    private static final int SCROLL=245;
    private static final int SCROLL_HEIGHT=ROW * ROW_SIZE;
    private static final int SCROLL_TOP=52;
    private static final int SCROLL_SIZE=24;
    private static final int TEXT=0xE0E0E0;
    private static final int TOOLTIP_TEXT=0xAAAAAA;
    private static final int STAT_TEXT=0xE2BD77;
    private static final int HEADER_TEXT=0xF2F2F2;
    private static final int TEXT_WIDTH=ROW_LEFT + OFFSET;
    private static final int PROGRESS=ROW_WIDTH - OFFSET - 8;
    private static final DecimalFormat DECIMAL_FORMAT=new DecimalFormat("0.####", DecimalFormatSymbols.getInstance(Locale.ROOT));
    private int visible;
    private boolean scrollbar;
    public PlayerStatScreen() {
        super(Component.translatable("screen.lendersdelight.stat.title"));
    }

    @Override
    public void render(GuiGraphics guiGraphics,int x,int y,float tick) {
        if(!ServerConfig.SPEC.isLoaded() || !ServerConfig.SHOW_STAT_SCREEN.get()) {
            onClose();
            return;
        }
        float value=getValue();
        int left=Math.round((width - SCREEN * value) / 2.0f);
        int top=Math.round((height - SCREEN * value) / 2.0f);
        int mouseX=(int) ((x - left) / value);
        int mouseY=(int) ((y - top) / value);
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(left,top,0);
        guiGraphics.pose().scale(value,value,1.0f);
        guiGraphics.blit(STAT_SCREEN,0,0,0,0,SCREEN,SCREEN,256,256);
        guiGraphics.drawCenteredString(font,title,SCREEN / 2,12,HEADER_TEXT);
        StatMode statMode= StatReloadListener.getStatMode();
        Component component=Component.translatable("screen.lendersdelight.stat.mode." + statMode.name().toLowerCase(Locale.ROOT));
        String string=mode(component.getString(),221);
        guiGraphics.drawCenteredString(font,string,SCREEN / 2,22,HEADER_TEXT);
        Player player=minecraft == null ? null : minecraft.player;
        IPlayerStat iPlayerStat=player == null ? null : ClientStat.get(player);
        int indexValue= -1;
        if(iPlayerStat == null) {
            guiGraphics.drawCenteredString(font,Component.translatable("screen.lendersdelight.stat.text"),SCREEN / 2,ROW_TOP + 75,TOOLTIP_TEXT);
        } else {
            visible=Math.max(0,Math.min(visible,getMaxScroll()));
            for (int index=0;index < ROW;index++) {
                int slot=visible + index;
                if(slot >= STAT_TYPES.length) break;
                int row=ROW_TOP + index * ROW_SIZE;
                boolean hover=drawInside(mouseX,mouseY,ROW_LEFT,row,ROW_WIDTH,ROW_HEIGHT);
                renderStat(guiGraphics,row,STAT_TYPES[slot],player,iPlayerStat,statMode,hover);
                if(hover && ServerConfig.isEnableStat(STAT_TYPES[slot]) && statMode == StatMode.LIMITED && ClientStatTooltipHelper.hasAttributeLimit(STAT_TYPES[slot])) indexValue=slot;
            }
            renderScrollbar(guiGraphics);
        }
        String stringDown=mode(Component.translatable("screen.lendersdelight.stat.down").getString(),226);
        guiGraphics.drawCenteredString(font,stringDown,SCREEN / 2,233,TOOLTIP_TEXT);
        guiGraphics.pose().popPose();
        if(indexValue >= 0) guiGraphics.renderTooltip(font,Component.translatable("screen.lendersdelight.stat.tooltip"),x,y);
    }

    @Override
    public boolean mouseScrolled(double x,double y,double mouseX,double mouseY) {
        if(mouseY != 0) {
            setScroll(mouseY > 0 ? -1 : 1);
            return true;
        }
        return super.mouseScrolled(x,y,mouseX,mouseY);
    }

    @Override
    public boolean mouseClicked(double x,double y,int value) {
        if(value == 0 && getMaxScroll() > 0) {
            float indexValue=getValue();
            int left=Math.round((width - SCREEN * indexValue) / 2.0f);
            int top=Math.round((height - SCREEN * indexValue) / 2.0f);
            int mouseX=(int) ((x - left) / indexValue);
            int mouseY=(int) ((y - top) / indexValue);
            if(drawInside(mouseX,mouseY,SCROLL - 2,SCROLL_TOP,9,SCROLL_HEIGHT)) {
                scrollbar=true;
                setScrollMouse(x,y);
                return true;
            }
        }
        return super.mouseClicked(x,y,value);
    }

    @Override
    public boolean mouseDragged(double x,double y,int value,double mouseX,double mouseY) {
        if(value == 0 && scrollbar) {
            setScrollMouse(x,y);
            return true;
        }
        return super.mouseDragged(x,y,value,mouseX,mouseY);
    }

    @Override
    public boolean mouseReleased(double x,double y,int value) {
        if(value == 0 && scrollbar) {
            scrollbar=false;
            return true;
        }
        return super.mouseReleased(x,y,value);
    }

    @Override
    public boolean keyPressed(int key,int scanValue,int value) {
        if(ClientKeyBind.OPEN_STAT.isActiveAndMatches(InputConstants.getKey(key,scanValue))) {
            onClose();
            return true;
        }
        if(key == GLFW.GLFW_KEY_DOWN) {
            setScroll(1);
            return true;
        }
        if(key == GLFW.GLFW_KEY_UP) {
            setScroll(-1);
            return true;
        }
        return super.keyPressed(key,scanValue,value);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    private void renderStat(GuiGraphics guiGraphics,int y,StatType statType,Player player,IPlayerStat iPlayerStat,StatMode statMode,boolean hover) {
        guiGraphics.blit(STAT_SPRITE, ROW_LEFT, y, 0, hover ? 32 : 0, ROW_WIDTH, ROW_HEIGHT, 256, 256);
        int value = statType.ordinal();
        guiGraphics.blit(STAT_ICON, ROW_LEFT + 5, y + 5, value * 16, 0, 16, 16, 144, 16);
        String string = "+" + format(iPlayerStat.getStat(statType)) + "%";
        int x = ROW_LEFT + ROW_WIDTH - 7 - font.width(string);
        int width=Math.max(14,x-TEXT_WIDTH - 6);
        String name = mode(Component.translatable(statType.getLangKey()).getString(), Math.max(14, x - TEXT_WIDTH - 5));
        boolean enable=ServerConfig.isEnableStat(statType);
        guiGraphics.drawString(font, name, TEXT_WIDTH, y + 3, TEXT, false);
        guiGraphics.drawString(font, string, x, y + 5,enable ? STAT_TEXT : TOOLTIP_TEXT, false);
        if(!enable) {
            guiGraphics.drawString(font,Component.translatable("screen.lendersdelight.stat.disable"),TEXT_WIDTH,y + 12, TOOLTIP_TEXT,false);
            return;
        }
        if (statMode != StatMode.LIMITED) return;
        double limitValue = ClientStatTooltipHelper.getLimitValue(player, iPlayerStat, statType);
        double limit = StatReloadListener.getLimit(statType);
        boolean attributeValue = ClientStatTooltipHelper.hasAttributeLimit(statType);
        String limitAttribute=format(limit) + (attributeValue ? "" : "%");
        Component component = Component.translatable("screen.lendersdelight.stat.limit",limitAttribute);
        guiGraphics.drawString(font, mode(component.getString(),width), TEXT_WIDTH, y + 12, TOOLTIP_TEXT, false);
        int progressY = y + 21;
        guiGraphics.blit(STAT_SPRITE, TEXT_WIDTH, progressY, 0, 64, PROGRESS, 2, 256, 256);
        double progress = limit <= 0 ? 1 : Math.max(0, Math.min(1, limitValue / limit));
        int progressValue = (int) Math.round(PROGRESS * progress);
        if (progressValue > 0) {
            guiGraphics.blit(STAT_SPRITE, TEXT_WIDTH, progressY, 0, 72, progressValue, 2, 256, 256);
        }
    }

        private void renderScrollbar(GuiGraphics guiGraphics) {
        if(getMaxScroll() == 0) return;
        guiGraphics.blit(STAT_SPRITE,SCROLL,SCROLL_TOP,232,0,5,SCROLL_HEIGHT,256,256);
        int value=SCROLL_HEIGHT - SCROLL_SIZE;
        int indexValue=Math.round((float) value * visible / getMaxScroll());
        guiGraphics.blit(STAT_SPRITE,SCROLL,SCROLL_TOP + indexValue,241,0,5,SCROLL_SIZE,256,256);
        }

        private String mode(String string,int value) {
        if(font.width(string) < value) return string;
        return font.plainSubstrByWidth(string,Math.max(0,value - 6)) + "..";
        }

        private static String format(double value) {
        if(!Double.isFinite(value)) return "?";
        if(value != 0 && Math.abs(value) < 0.00000001) return String.format(Locale.ROOT,"%.2e",value);
        return DECIMAL_FORMAT.format(value);
        }

        public static boolean drawInside(int x,int y, int left,int top,int width,int height) {
        return x >= left && x < left + width && y >= top && y < top + height;
        }

        private float getValue() {
        return Math.max(0.1f,Math.min(1.0f,Math.min((width - 8f) / SCREEN,(height - 8f) / SCREEN)));
        }

        private int getMaxScroll() {
        return Math.max(0,STAT_TYPES.length - ROW);
        }

        private void setScroll(int value) {
        visible=Math.max(0,Math.min(getMaxScroll(),visible + value));
        }

        private void setScrollMouse(double x,double y) {
        float value=getValue();
        int top=Math.round((height - SCREEN * value) / 2.0f);
        double mouseY=(y - top) / value;
        double mouseX=(mouseY - SCROLL_TOP - SCROLL_SIZE / 2) / (SCROLL_HEIGHT - SCROLL_SIZE);
        mouseX=Math.max(0,Math.min(1,mouseX));
        visible=(int) Math.round(mouseX * getMaxScroll());
        }
}
