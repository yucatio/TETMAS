package com.yucatio.tetmas.io;

import android.content.Context;
import android.util.Log;

import com.yucatio.tetmas.game.GameWorld;
import com.yucatio.tetmas.game.attribute.FieldSize;
import com.yucatio.tetmas.game.attribute.Stage;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class GameDataFile {
    private static final String TAG = "GameDataFile";
    private static final String KEY = "yucati0";
    private static final String ENCRYPT_ALGORITHM = "Blowfish";
    private Context context;
    private Stage stage;
    private FieldSize fieldSize;

    public GameDataFile(Context context, Stage stage, FieldSize fieldSize) {
        this.context = context;
        this.stage = stage;
        this.fieldSize = fieldSize;
    }


    public boolean exists() {
        File file = context.getFileStreamPath(getFileName());

        return file.exists();
    }
    public GameWorld load() throws IOException {
        ObjectInputStream ois = null;
        try {
            SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(), ENCRYPT_ALGORITHM);
            Cipher cipher = Cipher.getInstance(ENCRYPT_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            ois = new ObjectInputStream(new CipherInputStream(context.openFileInput(getFileName()), cipher));

            return (GameWorld) ois.readObject();
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "NoSuchAlgorithmException occurred. algorithm = " + ENCRYPT_ALGORITHM + ", stage=" + stage.getGameDataId(), e);
            throw new IOException(e);
        } catch (NoSuchPaddingException e) {
            Log.e(TAG, "NoSuchPaddingException occurred. stage=" + stage.getGameDataId(), e);
            throw new IOException(e);
        } catch (InvalidKeyException e) {
            Log.e(TAG, "InvalidKeyException occurred. stage=" + stage.getGameDataId(), e);
            throw new IOException(e);
        } catch (ClassNotFoundException e) {
            Log.e(TAG, "ClassNotFoundException occurred. stage=" + stage.getGameDataId(), e);
            throw new IOException(e);
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException ignore) {
                    Log.w(TAG, "IOException occurred when closing ObjectInputStream.", ignore);
                }
            }
        }
    }

    public void save(GameWorld gameWorld) throws IOException {
        ObjectOutputStream oos = null;
        try {
            SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(), ENCRYPT_ALGORITHM);
            Cipher cipher = Cipher.getInstance(ENCRYPT_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);

            oos = new ObjectOutputStream(new CipherOutputStream(context.openFileOutput(getFileName(), Context.MODE_PRIVATE), cipher));
            oos.writeObject(gameWorld);
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "NoSuchAlgorithmException occurred. algorithm = " + ENCRYPT_ALGORITHM + ", stage=" + stage.getGameDataId(), e);
            throw new IOException(e);
        } catch (NoSuchPaddingException e) {
            Log.e(TAG, "NoSuchPaddingException occurred. stage=" + stage.getGameDataId(), e);
            throw new IOException(e);
        } catch (InvalidKeyException e) {
            Log.e(TAG, "InvalidKeyException occurred. stage=" + stage.getGameDataId(), e);
            throw new IOException(e);
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException ignore) {
                    Log.w(TAG, "IOException occurred when closing ObjectOutputStream.", ignore);
                }
            }
        }
    }

    public void delete() {
        File file = context.getFileStreamPath(getFileName());
        file.delete();
    }

    private String getFileName() {
        return stage.getGameDataId() + "_" + fieldSize.getId() + ".dat";
    }


}
