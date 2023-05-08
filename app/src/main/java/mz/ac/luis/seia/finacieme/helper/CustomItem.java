package mz.ac.luis.seia.finacieme.helper;

public class CustomItem {
    private String spinnerName;
    private  int spinnerIconImage;

    public CustomItem(String spinnerName, int spinnerIconImage) {
        this.spinnerName = spinnerName;
        this.spinnerIconImage = spinnerIconImage;
    }

    public String getSpinnerName() {
        return spinnerName;
    }

    public int getSpinnerIconImage() {
        return spinnerIconImage;
    }


}
