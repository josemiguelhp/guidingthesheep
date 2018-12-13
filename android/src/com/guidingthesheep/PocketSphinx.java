package com.guidingthesheep;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.guidingthesheep.interfaces.SphinxToGdx;

import java.io.File;
import java.io.IOException;

import edu.cmu.pocketsphinx.Assets;
import edu.cmu.pocketsphinx.Hypothesis;
import edu.cmu.pocketsphinx.RecognitionListener;
import edu.cmu.pocketsphinx.SpeechRecognizer;
import edu.cmu.pocketsphinx.SpeechRecognizerSetup;

public class PocketSphinx implements RecognitionListener {
    //private static final String PHONE_SEARCH = "phones";
    private static final String MOVIMIENTO = "movimiento";
    private static final int PERMISSIONS_REQUEST_RECORD_AUDIO = 1;
    private static final int PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    private int currentSearch=2;

    private SpeechRecognizer recognizer;
    private SphinxToGdx sphinxToGdx;
    private Context context;

    public PocketSphinx(SphinxToGdx sphinxToGdx, Context context,Activity activity){
        this.sphinxToGdx=sphinxToGdx;
        this.context=context;
        // Check if user has given permission to record audio
        try {
            int permissionCheck = ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO);
            int permissionCheck2 = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.RECORD_AUDIO}, PERMISSIONS_REQUEST_RECORD_AUDIO);
            }
            if (permissionCheck2 != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
            }

        }catch( Exception e ) {

        }
        sphinxToGdx.onCommand("Iniciando reconocedor de voz.. espere toche");
        runRecognizerSetup();
        /*Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {//be carrefoul this is for any null pointer or error in the game
			@Override
			public void uncaughtException(Thread arg0, Throwable arg1) {
			    recognizer.shutdown();
				threadError(Texts.e3);
				recognizer=null;
		        runRecognizerSetup();
			}
        });*/
    }

    /*private void threadError(String error) {
    	sphinxToGdx.pocketSphinxError(error);
    }*/
    @SuppressLint("StaticFieldLeak")
    public void runRecognizerSetup() {
        // Recognizer initialization is a time-consuming and it involves IO,
        // so we execute it in async task
        new AsyncTask<Void, Void, Exception>() {
            @Override
            protected Exception doInBackground(Void... params) {
                try {
                    Assets assets = new Assets(context);
                    File assetDir = assets.syncAssets();
                    setupRecognizer(assetDir);
                } catch (IOException e) {
                    sphinxToGdx.pocketSphinxError("Necesitas aceptar los permisos..");
                    return e;
                }
                return null;
            }

            @Override
            protected void onPostExecute(Exception result) {
                if (result != null) {
                    sphinxToGdx.pocketSphinxError("Otra app esta usando el microfono, cierrala!!");
                }else
                    reset(currentSearch);
            }
        }.execute();
    }



    void dispose() {
        if (recognizer != null) {
            recognizer.cancel();
            recognizer.shutdown();
        }
    }
    void resume(){
        reset(currentSearch);
    }
    void pause(){
        if(recognizer!=null) {
            recognizer.stop();
            recognizer.cancel();
        }
    }

    /**
     * In partial result we get quick updates about current hypothesis. In
     * keyword spotting mode we can react here, in other modes we need to wait
     * for final result in onResult.
     */
    @Override
    public void onPartialResult(Hypothesis hypothesis) {
        if (hypothesis != null) {
            String text = hypothesis.getHypstr();
            //makeText(context, text, Toast.LENGTH_SHORT).show();
            sphinxToGdx.onCommand(text);
        }
    }

    /**
     * This callback is called when we stop the recognizer.
     */
    @Override
    public void onResult(Hypothesis hypothesis) {
        if (hypothesis != null) {
            String text = hypothesis.getHypstr();
            //makeText(context, text, Toast.LENGTH_SHORT).show();
            sphinxToGdx.onCommand(text);
        }
    }

    @Override
    public void onBeginningOfSpeech() {
    }

    /**
     * We stop recognizer here to get a final result
     */
    @Override
    public void onEndOfSpeech() {
        reset(currentSearch);
    }




    private void setupRecognizer(File assetsDir) throws IOException {

        recognizer = SpeechRecognizerSetup.defaultSetup()
                .setAcousticModel(new File(assetsDir, "es-ptm"))
                .setDictionary(new File(assetsDir, "es.dict"))
                //.setRawLogDir(assetsDir)
                //.setKeywordThreshold((float) 1e-1)
                .getRecognizer();

        recognizer.addListener(this);

        File movimientoGrammar = new File(assetsDir, "movimiento.gram");
        recognizer.addKeywordSearch(MOVIMIENTO, movimientoGrammar);
        /*File phoneticModel = new File(assetsDir, "en-phone.dmp");
        recognizer.addAllphoneSearch(PHONE_SEARCH, phoneticModel);*/
        sphinxToGdx.onCommand("...");

    }

    @Override
    public void onError(Exception error) {
        sphinxToGdx.pocketSphinxError("Fallo el reconocimiento de voz! tratando de reiniciar.. espere porfavor!");
    }

    @Override
    public void onTimeout() {
        reset(currentSearch);
    }

    public void reset(int numberOfSearch)
    {
        try {
            if(recognizer!=null) {
                recognizer.cancel();
                if(numberOfSearch==0||numberOfSearch==1) {
                    currentSearch=1;//nothing
                    sphinxToGdx.stopListening();
                }else if(numberOfSearch==2) {
                    recognizer.startListening(MOVIMIENTO);
                    currentSearch=2;
                }
            }
        } catch( Exception e ) {
            sphinxToGdx.pocketSphinxError("Fallo el reconocimiento de voz! tratando de reiniciar.. espere porfavor!");
            recognizer.shutdown();
            recognizer=null;
            runRecognizerSetup();

        }
    }
    public int getCurrentSearch() {
        return currentSearch;
    }
}

