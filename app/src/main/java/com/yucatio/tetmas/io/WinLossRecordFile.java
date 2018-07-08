package com.yucatio.tetmas.io;

import android.content.Context;
import android.util.Log;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yucatio.tetmas.game.attribute.FieldSize;
import com.yucatio.tetmas.game.attribute.Stage;
import com.yucatio.tetmas.game.attribute.WinLossRecord;

import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class WinLossRecordFile {
    private static final String TAG = "WinLossRecordFile";
    private static final String KEY = "yucati0";
    private static final String ENCRYPT_ALGORITHM = "Blowfish";

    /** 勝ち数、負け数最高値 */
    private  static final int COUNT_MAX = 9999;

    private Context context;
    private Stage stage;
    private FieldSize fieldSize;

    public WinLossRecordFile(Context context, Stage stage, FieldSize fieldSize) {
        this.context = context;
        this.stage = stage;
        this.fieldSize = fieldSize;
    }

    public WinLossRecord getWinLossRecord() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        WinLossRecord record;

        // ファイル存在確認
        if (! fileExists()) {
            return new WinLossRecord();
        }

        try {
            SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(), ENCRYPT_ALGORITHM);
            Cipher cipher = Cipher.getInstance(ENCRYPT_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, keySpec);

            record = mapper.readValue(new CipherInputStream(context.openFileInput(getFileName()), cipher), WinLossRecord.class);

            return record;
        } catch (JsonParseException e) {
            Log.e(TAG, "JsonParseException occurred. file=" + getFileName(), e);
            throw new IOException(e);
        } catch (JsonMappingException e) {
            Log.e(TAG, "JsonMappingException occurred. file=" + getFileName(), e);
            throw new IOException(e);
        } catch (IOException e) {
            Log.e(TAG, "IOException occurred. file=" + getFileName(), e);
            throw e;
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "NoSuchAlgorithmException occurred. file=" + getFileName(), e);
            throw new IOException(e);
        } catch (NoSuchPaddingException e) {
            Log.e(TAG, "NoSuchPaddingException occurred. file=" + getFileName(), e);
            throw new IOException(e);
        } catch (InvalidKeyException e) {
            Log.e(TAG, "InvalidKeyException occurred. file=" + getFileName(), e);
            throw new IOException(e);
        }

    }

    public void recordWin() throws IOException {
        WinLossRecord record = getWinLossRecord();

        record.setWinCount(Math.min(COUNT_MAX, record.getWinCount() + 1));

        save(record);
    }

    public void recordDraw() throws IOException {
        WinLossRecord record = getWinLossRecord();

        record.setEvenCount(Math.min(COUNT_MAX, record.getEvenCount() + 1));

        save(record);
    }

    public void recordLose() throws IOException {
        WinLossRecord record = getWinLossRecord();

        record.setLossCount(Math.min(COUNT_MAX, record.getLossCount() + 1));

        save(record);
    }


    public void save(WinLossRecord record) throws IOException {
        record.setTimestamp(System.currentTimeMillis());

        ObjectMapper mapper = new ObjectMapper();

        try {
            SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(), ENCRYPT_ALGORITHM);
            Cipher cipher = Cipher.getInstance(ENCRYPT_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);

            mapper.writeValue(new CipherOutputStream(context.openFileOutput(getFileName(), Context.MODE_PRIVATE), cipher), record);
        } catch (JsonGenerationException | JsonMappingException e) {
            Log.e(TAG, "JsonParseException occurred. object=" + record, e);
            throw new IOException(e);
        } catch (IOException e) {
            Log.e(TAG, "IOException occurred. file=" + getFileName(), e);
            throw e;
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "NoSuchAlgorithmException occurred. file=" + getFileName(), e);
            throw new IOException(e);
        } catch (NoSuchPaddingException e) {
            Log.e(TAG, "NoSuchPaddingException occurred. file=" + getFileName(), e);
            throw new IOException(e);
        } catch (InvalidKeyException e) {
            Log.e(TAG, "InvalidKeyException occurred. file=" + getFileName(), e);
            throw new IOException(e);
        }

    }

    private String getFileName() {
        return stage.getWinLossRecordFileName() + "_" + fieldSize.getId() + ".winlossrecord.dat";
    }

    private boolean fileExists() {
        File file = context.getFileStreamPath(getFileName());
        return file.exists();
    }

}

