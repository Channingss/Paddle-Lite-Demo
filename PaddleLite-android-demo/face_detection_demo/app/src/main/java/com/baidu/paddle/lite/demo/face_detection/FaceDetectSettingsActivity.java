package com.baidu.paddle.lite.demo.face_detection;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.support.v7.app.ActionBar;

import com.baidu.paddle.lite.demo.face_detection.core.AppCompatPreferenceActivity;

import java.util.ArrayList;
import java.util.List;

public class FaceDetectSettingsActivity extends AppCompatPreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    ListPreference lpChoosePreInstalledModel = null;
    CheckBoxPreference cbEnableCustomSettings = null;
    EditTextPreference etModelPath = null;
    EditTextPreference etLabelPath = null;
    EditTextPreference etImagePath = null;
    ListPreference lpCPUThreadNum = null;
    ListPreference lpCPUPowerMode = null;
    ListPreference lpInputColorFormat = null;
    EditTextPreference etInputShape = null;
    EditTextPreference etInputMean = null;
    EditTextPreference etInputStd = null;

    List<String> preInstalledModelPaths = null;
    List<String> preInstalledLabelPaths = null;
    List<String> preInstalledImagePaths = null;
    List<String> preInstalledInputShapes = null;
    List<String> preInstalledCPUThreadNums = null;
    List<String> preInstalledCPUPowerModes = null;
    List<String> preInstalledInputColorFormats = null;
    List<String> preInstalledInputMeans = null;
    List<String> preInstalledInputStds = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings_face_detect);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        // initialized pre-installed models
        preInstalledModelPaths = new ArrayList<String>();
        preInstalledLabelPaths = new ArrayList<String>();
        preInstalledImagePaths = new ArrayList<String>();
        preInstalledInputShapes = new ArrayList<String>();
        preInstalledCPUThreadNums = new ArrayList<String>();
        preInstalledCPUPowerModes = new ArrayList<String>();
        preInstalledInputColorFormats = new ArrayList<String>();
        preInstalledInputMeans = new ArrayList<String>();
        preInstalledInputStds = new ArrayList<String>();
        // add fused_deeplab_for_cpu
        preInstalledModelPaths.add(getString(R.string.FD_MODEL_PATH_DEFAULT));
        preInstalledLabelPaths.add(getString(R.string.FD_LABEL_PATH_DEFAULT));
        preInstalledImagePaths.add(getString(R.string.FD_IMAGE_PATH_DEFAULT));
        preInstalledCPUThreadNums.add(getString(R.string.FD_CPU_THREAD_NUM_DEFAULT));
        preInstalledCPUPowerModes.add(getString(R.string.FD_CPU_POWER_MODE_DEFAULT));
        preInstalledInputColorFormats.add(getString(R.string.FD_INPUT_COLOR_FORMAT_DEFAULT));
        preInstalledInputShapes.add(getString(R.string.FD_INPUT_SHAPE_DEFAULT));
        preInstalledInputMeans.add(getString(R.string.FD_INPUT_MEAN_DEFAULT));
        preInstalledInputStds.add(getString(R.string.FD_INPUT_STD_DEFAULT));

        // initialize UI components
        lpChoosePreInstalledModel =
                (ListPreference) findPreference(getString(R.string.FD_CHOOSE_PRE_INSTALLED_MODEL_KEY));
        String[] preInstalledModelNames = new String[preInstalledModelPaths.size()];
        for (int i = 0; i < preInstalledModelPaths.size(); i++) {
            preInstalledModelNames[i] =
                    preInstalledModelPaths.get(i).substring(preInstalledModelPaths.get(i).lastIndexOf("/") + 1);
        }
        lpChoosePreInstalledModel.setEntries(preInstalledModelNames);
        lpChoosePreInstalledModel.setEntryValues(preInstalledModelPaths.toArray(new String[preInstalledModelPaths.size()]));
        cbEnableCustomSettings =
                (CheckBoxPreference) findPreference(getString(R.string.FD_ENABLE_CUSTOM_SETTINGS_KEY));
        etModelPath = (EditTextPreference) findPreference(getString(R.string.FD_MODEL_PATH_KEY));
        etModelPath.setTitle("Model Path (SDCard: " + Utils.getSDCardDirectory() + ")");
        etLabelPath = (EditTextPreference) findPreference(getString(R.string.FD_LABEL_PATH_KEY));
        etImagePath = (EditTextPreference) findPreference(getString(R.string.FD_IMAGE_PATH_KEY));
        lpCPUThreadNum =
                (ListPreference) findPreference(getString(R.string.FD_CPU_THREAD_NUM_KEY));
        lpCPUPowerMode =
                (ListPreference) findPreference(getString(R.string.FD_CPU_POWER_MODE_KEY));
        lpInputColorFormat =
                (ListPreference) findPreference(getString(R.string.FD_INPUT_COLOR_FORMAT_KEY));
        etInputShape = (EditTextPreference) findPreference(getString(R.string.FD_INPUT_SHAPE_KEY));
        etInputMean = (EditTextPreference) findPreference(getString(R.string.FD_INPUT_MEAN_KEY));
        etInputStd = (EditTextPreference) findPreference(getString(R.string.FD_INPUT_STD_KEY));
    }

    private void reloadPreferenceAndUpdateUI() {
        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        boolean enableCustomSettings =
                sharedPreferences.getBoolean(getString(R.string.FD_ENABLE_CUSTOM_SETTINGS_KEY), false);
        String modelPath = sharedPreferences.getString(getString(R.string.FD_CHOOSE_PRE_INSTALLED_MODEL_KEY),
                getString(R.string.FD_MODEL_PATH_DEFAULT));
        int modelIdx = lpChoosePreInstalledModel.findIndexOfValue(modelPath);
        if (modelIdx >= 0 && modelIdx < preInstalledModelPaths.size()) {
            if (!enableCustomSettings) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(getString(R.string.FD_MODEL_PATH_KEY), preInstalledModelPaths.get(modelIdx));
                editor.putString(getString(R.string.FD_LABEL_PATH_KEY), preInstalledLabelPaths.get(modelIdx));
                editor.putString(getString(R.string.FD_IMAGE_PATH_KEY), preInstalledImagePaths.get(modelIdx));
                editor.putString(getString(R.string.FD_CPU_THREAD_NUM_KEY), preInstalledCPUThreadNums.get(modelIdx));
                editor.putString(getString(R.string.FD_CPU_POWER_MODE_KEY), preInstalledCPUPowerModes.get(modelIdx));
                editor.putString(getString(R.string.FD_INPUT_COLOR_FORMAT_KEY),
                        preInstalledInputColorFormats.get(modelIdx));
                editor.putString(getString(R.string.FD_INPUT_SHAPE_KEY), preInstalledInputShapes.get(modelIdx));
                editor.putString(getString(R.string.FD_INPUT_MEAN_KEY), preInstalledInputMeans.get(modelIdx));
                editor.putString(getString(R.string.FD_INPUT_STD_KEY), preInstalledInputStds.get(modelIdx));
                editor.commit();
            }
            lpChoosePreInstalledModel.setSummary(modelPath);
        }
        cbEnableCustomSettings.setChecked(enableCustomSettings);
        etModelPath.setEnabled(enableCustomSettings);
        etLabelPath.setEnabled(enableCustomSettings);
        etImagePath.setEnabled(enableCustomSettings);
        lpCPUThreadNum.setEnabled(enableCustomSettings);
        lpCPUPowerMode.setEnabled(enableCustomSettings);
        lpInputColorFormat.setEnabled(enableCustomSettings);
        etInputShape.setEnabled(enableCustomSettings);
        etInputMean.setEnabled(enableCustomSettings);
        etInputStd.setEnabled(enableCustomSettings);
        modelPath = sharedPreferences.getString(getString(R.string.FD_MODEL_PATH_KEY),
                getString(R.string.FD_MODEL_PATH_DEFAULT));
        String labelPath = sharedPreferences.getString(getString(R.string.FD_LABEL_PATH_KEY),
                getString(R.string.FD_LABEL_PATH_DEFAULT));
        String imagePath = sharedPreferences.getString(getString(R.string.FD_IMAGE_PATH_KEY),
                getString(R.string.FD_IMAGE_PATH_DEFAULT));
        String cpuThreadNum = sharedPreferences.getString(getString(R.string.FD_CPU_THREAD_NUM_KEY),
                getString(R.string.FD_CPU_THREAD_NUM_DEFAULT));
        String cpuPowerMode = sharedPreferences.getString(getString(R.string.FD_CPU_POWER_MODE_KEY),
                getString(R.string.FD_CPU_POWER_MODE_DEFAULT));
        String inputColorFormat = sharedPreferences.getString(getString(R.string.FD_INPUT_COLOR_FORMAT_KEY),
                getString(R.string.FD_INPUT_COLOR_FORMAT_DEFAULT));
        String inputShape = sharedPreferences.getString(getString(R.string.FD_INPUT_SHAPE_KEY),
                getString(R.string.FD_INPUT_SHAPE_DEFAULT));
        String inputMean = sharedPreferences.getString(getString(R.string.FD_INPUT_MEAN_KEY),
                getString(R.string.FD_INPUT_MEAN_DEFAULT));
        String inputStd = sharedPreferences.getString(getString(R.string.FD_INPUT_STD_KEY),
                getString(R.string.FD_INPUT_STD_DEFAULT));
        etModelPath.setSummary(modelPath);
        etModelPath.setText(modelPath);
        etLabelPath.setSummary(labelPath);
        etLabelPath.setText(labelPath);
        etImagePath.setSummary(imagePath);
        etImagePath.setText(imagePath);
        lpCPUThreadNum.setValue(cpuThreadNum);
        lpCPUThreadNum.setSummary(cpuThreadNum);
        lpCPUPowerMode.setValue(cpuPowerMode);
        lpCPUPowerMode.setSummary(cpuPowerMode);
        lpInputColorFormat.setValue(inputColorFormat);
        lpInputColorFormat.setSummary(inputColorFormat);
        etInputShape.setSummary(inputShape);
        etInputShape.setText(inputShape);
        etInputMean.setSummary(inputMean);
        etInputMean.setText(inputMean);
        etInputStd.setSummary(inputStd);
        etInputStd.setText(inputStd);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        reloadPreferenceAndUpdateUI();
    }

    @Override
    protected void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.FD_CHOOSE_PRE_INSTALLED_MODEL_KEY))) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(getString(R.string.FD_ENABLE_CUSTOM_SETTINGS_KEY), false);
            editor.commit();
        }
        reloadPreferenceAndUpdateUI();
    }
}