package model;

public class LineGraphSettings {

    private int[] dataSet1;
    private int[] dataSet2;
    private String xAxisLabel;
    private String graphTitle;
    private String data1Title;
    private String data2Title;

    public LineGraphSettings(int[] dataSet1, int[] dataSet2, String xAxisLabel, String graphTitle, String data1Title, String data2Title) {

        this.dataSet1 = dataSet1;
        this.dataSet2 = dataSet2;
        this.xAxisLabel = xAxisLabel;
        this.graphTitle = graphTitle;
        this.data1Title = data1Title;
        this.data2Title = data2Title;
    }

    public int[] getDataSet1() {
        return dataSet1;
    }

    public void setDataSet1(int[] dataSet1) {
        this.dataSet1 = dataSet1;
    }

    public int[] getDataSet2() {
        return dataSet2;
    }

    public void setDataSet2(int[] dataSet2) {
        this.dataSet2 = dataSet2;
    }

    public String getxAxisLabel() {
        return xAxisLabel;
    }

    public void setxAxisLabel(String xAxisLabel) {
        this.xAxisLabel = xAxisLabel;
    }

    public String getGraphTitle() {
        return graphTitle;
    }

    public void setGraphTitle(String graphTitle) {
        this.graphTitle = graphTitle;
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

}
