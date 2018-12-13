package com.guidingthesheep.utils;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;

public class FileHandleLoader extends AsynchronousAssetLoader <FileHandle,FileHandleLoader.FileParameter>{
    public FileHandleLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    private FileHandle file;
    @Override
    public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, FileParameter parameter) {
        return null;
    }

    @Override
    public void loadAsync(AssetManager manager, String fileName, FileHandle file, FileParameter parameter) {
        this.file = null;
        this.file = file;
    }

    @Override
    public FileHandle loadSync(AssetManager manager, String fileName, FileHandle file, FileParameter parameter) {
        FileHandle file2 = this.file;
        this.file = null;
        return file2;
    }

    static class FileParameter extends AssetLoaderParameters<FileHandle> {
    }
}
