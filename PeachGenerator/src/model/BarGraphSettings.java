package model;

public class BarGraphSettings {

    private int[] dataset1;
    private int[] dataset2;

    private String[] xAxisLabels;

    private String GraphTitle;

    private String data1Title;
    private String data2Title;

    private String xAxisTitle;
    private String yAxisTitle;

    public BarGraphSettings(int[] dataset1, int[] dataset2, String[] xAxisLabels, String graphTitle, String data1Title, String data2Title, String xAxisTitle, String yAxisTitle) {
        this.dataset1 = dataset1;
        this.dataset2 = dataset2;
        this.xAxisLabels = xAxisLabels;
        GraphTitle = graphTitle;
        this.data1Title = data1Title;
        this.data2Title = data2Title;
        this.xAxisTitle = xAxisTitle;
        this.yAxisTitle = yAxisTitle;
    }

    public int[] getDataset1() {
        return dataset1;
    }

    public void setDataset1(int[] dataset1) {
        this.dataset1 = dataset1;
    }

    public int[] getDataset2() {
        return dataset2;
    }

    public void setDataset2(int[] dataset2) {
        this.dataset2 = dataset2;
    }

    public String[] getxAxisLabel() {
        return xAxisLabels;
    }

    public void setxAxisLabel(String[] xAxisLabel) {
        this.xAxisLabels = xAxisLabel;
    }

    public String getGraphTitle() {
        return GraphTitle;
    }

    public void setGraphTitle(String graphTitle) {
        GraphTitle = graphTitle;
    }

    public String getData1Title() {
        return data1Title;
    }

    public void setData1Title(String data1Title) {
        this.data1Title = data1Title;
    }

    public String getData2Title() {
        return data2Title;
    }

    public void setData2Title(String data2Title) {
        this.data2Title = data2Title;
    }

    public String getxAxisTitle() {
        return xAxisTitle;
    }

    public void setxAxisTitle(String xAxisTitle) {
        this.xAxisTitle = xAxisTitle;
    }

    public String getyAxisTitle() {
        return yAxisTitle;
    }

    public void setyAxisTitle(String yAxisTitle) {
        this.yAxisTitle = yAxisTitle;
    }
}
