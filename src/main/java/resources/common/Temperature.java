package resources.common;

public enum Temperature {
  FAHRENHEIT("f"), CELCIUS("c");

  private String unit;

  Temperature(String unit) {
    this.unit = unit;
  }

  @Override
  public String toString() {
    return unit;
  }
}
