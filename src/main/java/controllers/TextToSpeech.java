package controllers;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

import javax.speech.Central;
import javax.speech.EngineException;

public class TextToSpeech {
    public static void main(String[] args) throws EngineException {
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        Central.registerEngineCentral("com.sun.speech.freetts.jsapi.FreeTTSEngineCentral");
        Voice voice;
        VoiceManager voiceManager = VoiceManager.getInstance();
        voice = voiceManager.getVoice("kevin16");
        voice.allocate();
        voice.speak("Welcome back "+Login.getCurrentUser().getFull_name());
    }
}
