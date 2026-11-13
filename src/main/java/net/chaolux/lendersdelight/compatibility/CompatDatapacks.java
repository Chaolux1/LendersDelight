package net.chaolux.lendersdelight.compatibility;

import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.forgespi.language.IModFileInfo;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

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
            addPackFindersEvent.addRepositorySource(consumer -> {
                Pack pack=Pack.readMetaAndCreate("lendersdelight:" + folder,Component.literal("Lender's Delight - " + string),true,name -> new PathPackResources(name,path,true),PackType.SERVER_DATA,Pack.Position.TOP,PackSource.BUILT_IN);
                if(pack != null) consumer.accept(pack);
            });
        }
    }
}
