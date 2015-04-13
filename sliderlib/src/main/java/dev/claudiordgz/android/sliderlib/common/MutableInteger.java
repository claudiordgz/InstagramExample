package dev.claudiordgz.android.sliderlib.common;

/**
 * Created by Android1 on 1/24/2015.
 */
public class MutableInteger {
  private int value;

  public MutableInteger(int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }

  public void setValue(int value) {
    this.value = value;
  }
}
