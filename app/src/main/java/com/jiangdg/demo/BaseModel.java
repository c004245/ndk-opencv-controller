package com.jiangdg.demo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Locale;

import lombok.Data;

@Data
public class BaseModel implements Parcelable {

  private String code;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  private String code_msg;

  public String getCode_msg() {
    return code_msg;
  }

  public void setCode_msg(String code_msg) {
    this.code_msg = code_msg;
  }

  private String member_idx;

  public String getMember_idx() {
    return member_idx;
  }

  public void setMember_idx(String member_idx) {
    this.member_idx = member_idx;
  }

  private String device_os;
  private String gcm_key;
  private String device_id;
  boolean success;

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  private String lang;

  public BaseModel() {

    String language = Locale.getDefault().getLanguage();
    Locale.getDefault().getISO3Language();
    Locale.getDefault().getCountry();
    Locale.getDefault().getISO3Country();
    Locale.getDefault().getDisplayCountry();
    Locale.getDefault().getDisplayName();
    Locale.getDefault().toString();
    Locale.getDefault().getDisplayLanguage();

    lang = language;
    /*
    if (language.equals("ko")) {
      lang = "ko";
    } else {
      lang = "en";
    }*/
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {

  }

  public String getDevice_os() {
    return device_os;
  }

  public void setDevice_os(String device_os) {
    this.device_os = device_os;
  }

  public String getGcm_key() {
    return gcm_key;
  }

  public void setGcm_key(String gcm_key) {
    this.gcm_key = gcm_key;
  }

  public String getDevice_id() {
    return device_id;
  }

  public void setDevice_id(String device_id) {
    this.device_id = device_id;
  }

  public String getLang() {
    return lang;
  }

  public void setLang(String lang) {
    this.lang = lang;
  }
}
