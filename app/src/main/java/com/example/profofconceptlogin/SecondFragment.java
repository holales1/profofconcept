package com.example.profofconceptlogin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.example.profofconceptlogin.databinding.FragmentSecondBinding;
import com.macasaet.fernet.Key;
import com.macasaet.fernet.StringValidator;
import com.macasaet.fernet.Token;
import com.macasaet.fernet.Validator;


import org.bouncycastle.jce.provider.BouncyCastleProvider;


import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;
import javax.crypto.spec.SecretKeySpec;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;

    private static String simkey = "eL5iGq4a-2Yn2pHIulbdmx7MhjXo5Ot2UnEDVS-4wuo=";
    private static String encryptedTextStatic= "gAAAAABkmME5tqkxt_44uyo8Rr-8qI-FahtNMXe5oSREaEsDhNuG9RLelRGSuKg_aQtNhqzW5XpM0otEu-GMQkCyJBPTHLudt2sgsG_R2VacFYY8qFsK7BO2BEhB0zMQsZCOBeFk1K_cAdaEXNWq9lBEgZRCSeNMSLCJN8llXYOZFUv-tRl_JYGze4_GUx3f0EefT5NfkPFsrUpYWLKD5rVOZ7ct-H6ko2BQ36_AV6i_OvGU-yhU3LtxyYwecszgixR-vlsT2bj5";

    private static String decryptionKeyStatic = "gAAAAABkmME5lIRZyk-9W-89z41bUGlOXKGCcFB_rS6lbbOsUNirmVVrXU-3A_gSoneh04R7LhEwTBMeNMC_OwLsCmZkOj_sknnoYQmTAPEl-MMMxSBp6RuTI1E3tV2pKilYnlcjBbziBdpo5k_1-Qgp1or9_Mr--d8ptqBuw0pYYQA-5fsSoK3vUuPseJRqqzRD8N0nE-48zuzlIJ8DU0hXtcBYI9Vsufy6AY2Vv3R-xgTRGvvd4YEZ7MR53DEKFE7m1RwdwVfbq0lX_q2taCEANuhtMR3iKn9a4nRY7yJPVak16YROMBkgjM6Z3UcNC_3MMKyT91vDc21JXbRBRYWTfZJLkjuUh7UPa5zvd-PhC1WhTjTlQI2k6IfIOTyDbkUZBnKEGfHMOT1OXYXXdcv27U5XwL40ZWK0l8p1Xol9NtqDtyTkv5XAcC4B0u5SSgYPPAt3HAIMtm_lbCSHQZaNm7bkuiBRJBIa2USuFl-Al47Lz-SI4cpJnFv39rGIgFTurnPem9XhTN3UUMKMz081JOUJz1yIP4ZL--v14vYeC2z68W40Vry5TaE2INrWX6_ee-t25dTW83rolb2hR2ST2Y_ZG2SoSYUtbNVFGHij8z0VmRchBKnqyj-kgmnu7NiQ88_q1DtVVZ-ZLz4i3qFhC-kZC8ekyPV7GuN0ZFdnla3TZ1hKmcCMnW7fqxFEJx_nk4Of_TBkdBPKHzhdaOkBQanmpA0HSByyVtd1cMoqWQ6jPuJQEgn26cGxvymGR1pfaaMxpRR5rrn7GQAALmc9-VrQ1l_MZ8Eezh_WvEfbV5pI6EAXWLDlNBnQq6Up_GhKNudT_0VwSdtWRW6QGRc6KxmPKGYsRu3Rcs9pZOQzKGMGBlqFOwYb-ESxUz46biZer8CHckSFe1Wjda6jiTRr_DkcKOl9B6kMEw_ghl50WTW8BrXMo5aTuPNAROci0VJQT8yHJjcwqQAQUW6eb4z5jtt0NZBo7CU9Z2mGNFMtPQJaDb97Ggh2Ve9vvD4EuJQ3hKX_YWo6rxvfcX9xR9Rh_UXiUbIwdFDVHmwDNsRML-o6H2dKr3cdv92Z1xLSPspRKPScjgrNXxll6x_9TpUMJKZQ0fEUi_DWjErBPBelPukIwmOoLkXq426-KwyHJl0MJX3P-KSm-X_oMKAForzBViBGzkJU2wopbd-fpotbciPqL2yfVfANPBtDizTmC6pW0dHE-FY9KFgmD_2tI8ZGZrh5zjZIt9XCJVCEFyyXNLDCXW01AH0=";


    private static final String ALGORITHM = "AES/CBC/PKCS7Padding";

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        if(!Python.isStarted()){
            Python.start(new AndroidPlatform(getActivity().getApplicationContext()));
        }
        Python python = Python.getInstance();
        PyObject module = python.getModule("my_module");
        PyObject result = module.callAttr("decryption_complete", simkey,decryptionKeyStatic,encryptedTextStatic);
        String w = result.toString();
        Security.addProvider(new BouncyCastleProvider());
        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                binding.textviewSecond.setText(redirectToMain());
            }
        });
    }

    private String redirectToMain(){
        String encryptedText= "gAAAAABkmME5tqkxt_44uyo8Rr-8qI-FahtNMXe5oSREaEsDhNuG9RLelRGSuKg_aQtNhqzW5XpM0otEu-GMQkCyJBPTHLudt2sgsG_R2VacFYY8qFsK7BO2BEhB0zMQsZCOBeFk1K_cAdaEXNWq9lBEgZRCSeNMSLCJN8llXYOZFUv-tRl_JYGze4_GUx3f0EefT5NfkPFsrUpYWLKD5rVOZ7ct-H6ko2BQ36_AV6i_OvGU-yhU3LtxyYwecszgixR-vlsT2bj5";

        String encryptedTextDecryptedAES = "";
        encryptedTextDecryptedAES = fernetDecrypt(encryptedText);
        byte [] byteArray = Base64.decode(encryptedTextDecryptedAES, Base64.URL_SAFE);
        String result = decryptRSA(encryptedTextDecryptedAES.getBytes(StandardCharsets.UTF_8));
        return result;
    }

    private String deleteExtraText(String keyDecrypted){
        return keyDecrypted
                .replace("-----BEGIN RSA PRIVATE KEY-----", "")
                .replace("-----END RSA PRIVATE KEY-----", "")
                .replace("\n", "")
                .trim();
    }

    private String deleteExtraTextPublic(String keyDecrypted){
        return keyDecrypted
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replace("\n", "")
                .trim();
    }

    private PrivateKey createPrivateKey(String keyDecrypted){
        byte[] keyBytes = Base64.decode(keyDecrypted, Base64.NO_PADDING);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(java.util.Base64.getDecoder().decode(keyDecrypted));

        KeyFactory keyFactory = null;
        PublicKey publicKey = null;
        PrivateKey privateKey = null;
        try {
            keyFactory = KeyFactory.getInstance("RSA");
            privateKey = keyFactory.generatePrivate(spec);
            //publicKey = keyFactory.generatePublic(spec);
        } catch (Exception e ) {
            throw new RuntimeException(e);
        }

        return privateKey;
    }

    private PublicKey createPublicKey(String keyDecrypted){
        byte[] keyBytes = Base64.decode(keyDecrypted, Base64.DEFAULT);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);

        KeyFactory keyFactory = null;
        PublicKey publicKey = null;
        try {
            keyFactory = KeyFactory.getInstance("RSA");
            publicKey = keyFactory.generatePublic(spec);

        } catch (Exception e ) {
            throw new RuntimeException(e);
        }
        return publicKey;
    }

    private byte[] getDecrypted(PrivateKey privateKey, byte[] encryptedData){
        Cipher cipher = null;
        byte[] decryptedData = null;
        try {
            cipher = Cipher.getInstance("RSA/ECB/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            decryptedData = cipher.doFinal(encryptedData);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return decryptedData;
    }

    public String decryptRSA(byte[] encryptedData) {
        String keyString = "gAAAAABkmME5lIRZyk-9W-89z41bUGlOXKGCcFB_rS6lbbOsUNirmVVrXU-3A_gSoneh04R7LhEwTBMeNMC_OwLsCmZkOj_sknnoYQmTAPEl-MMMxSBp6RuTI1E3tV2pKilYnlcjBbziBdpo5k_1-Qgp1or9_Mr--d8ptqBuw0pYYQA-5fsSoK3vUuPseJRqqzRD8N0nE-48zuzlIJ8DU0hXtcBYI9Vsufy6AY2Vv3R-xgTRGvvd4YEZ7MR53DEKFE7m1RwdwVfbq0lX_q2taCEANuhtMR3iKn9a4nRY7yJPVak16YROMBkgjM6Z3UcNC_3MMKyT91vDc21JXbRBRYWTfZJLkjuUh7UPa5zvd-PhC1WhTjTlQI2k6IfIOTyDbkUZBnKEGfHMOT1OXYXXdcv27U5XwL40ZWK0l8p1Xol9NtqDtyTkv5XAcC4B0u5SSgYPPAt3HAIMtm_lbCSHQZaNm7bkuiBRJBIa2USuFl-Al47Lz-SI4cpJnFv39rGIgFTurnPem9XhTN3UUMKMz081JOUJz1yIP4ZL--v14vYeC2z68W40Vry5TaE2INrWX6_ee-t25dTW83rolb2hR2ST2Y_ZG2SoSYUtbNVFGHij8z0VmRchBKnqyj-kgmnu7NiQ88_q1DtVVZ-ZLz4i3qFhC-kZC8ekyPV7GuN0ZFdnla3TZ1hKmcCMnW7fqxFEJx_nk4Of_TBkdBPKHzhdaOkBQanmpA0HSByyVtd1cMoqWQ6jPuJQEgn26cGxvymGR1pfaaMxpRR5rrn7GQAALmc9-VrQ1l_MZ8Eezh_WvEfbV5pI6EAXWLDlNBnQq6Up_GhKNudT_0VwSdtWRW6QGRc6KxmPKGYsRu3Rcs9pZOQzKGMGBlqFOwYb-ESxUz46biZer8CHckSFe1Wjda6jiTRr_DkcKOl9B6kMEw_ghl50WTW8BrXMo5aTuPNAROci0VJQT8yHJjcwqQAQUW6eb4z5jtt0NZBo7CU9Z2mGNFMtPQJaDb97Ggh2Ve9vvD4EuJQ3hKX_YWo6rxvfcX9xR9Rh_UXiUbIwdFDVHmwDNsRML-o6H2dKr3cdv92Z1xLSPspRKPScjgrNXxll6x_9TpUMJKZQ0fEUi_DWjErBPBelPukIwmOoLkXq426-KwyHJl0MJX3P-KSm-X_oMKAForzBViBGzkJU2wopbd-fpotbciPqL2yfVfANPBtDizTmC6pW0dHE-FY9KFgmD_2tI8ZGZrh5zjZIt9XCJVCEFyyXNLDCXW01AH0=";
        String keyDecryptedAES = "";

        keyDecryptedAES = fernetDecrypt(keyString);
        keyDecryptedAES = deleteExtraText(keyDecryptedAES);
        PrivateKey privateKey = createPrivateKey(keyDecryptedAES);
        Signature signature = null;
        byte[] test;
        try {
            signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(privateKey);
            signature.update(encryptedData);
            test = signature.sign();
            test=test;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (SignatureException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }

        byte[] decryptedByteData = getDecrypted(privateKey, test);

        return Base64.encodeToString(decryptedByteData, Base64.NO_CLOSE);
    }

    public static String fernetDecrypt(String keyString){
        final Key key = new Key(simkey);
        final Token token = Token.fromString(keyString);

        final Validator < String > validator = new StringValidator() {
            public TemporalAmount getTimeToLive() {
                return Duration.ofSeconds(Instant.MAX.getEpochSecond());
            }
        };
        final String payload = token.validateAndDecrypt(key, validator);
        return payload;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



}