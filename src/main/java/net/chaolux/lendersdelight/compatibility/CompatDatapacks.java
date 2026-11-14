package net.chaolux.lendersdelight.compatibility;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforgespi.language.IModFileInfo;

import java.nio.file.Files;
import java.nio.file.Path;

public class CompatDatapacks {
    public static void onAddPackFinders(AddPackFindersEvent addPackFindersEvent) {
        if(addPackFindersEvent.getPackType() != PackType.SERVER_DATA) return;
        IModFileInfo iModFileInfo= ModList.get().getModFileById("lendersdelight");
        if(iModFileInfo == null) return;
        for(var entry : CompatManager.getDatapacks().entrySet()) {
            String string=entry.getKey();
            String folder=entry.getValue();
            if(!CompatManager.isPrefer(string)) continue;
            Path path=iModFileInfo.getFile().findResource("resourcepacks/" + folder);
            if(!Files.isDirectory(path)) continue;
            addPackFindersEvent.addPackFinders(ResourceLocation.fromNamespaceAndPath("lendersdelight","resourcepacks/" + folder),PackType.SERVER_DATA,Component.literal("Lender's Delight - " + string),PackSource.BUILT_IN,true,Pack.Position.TOP);
        }
    }
}
