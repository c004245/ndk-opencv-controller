package com.jiangdg.demo;

import java.util.ArrayList;

import lombok.Data;

@Data
public class PillModel extends BaseListModel {

    private String userid;
    String folder_id;

    public String getFolder_id() {
        return folder_id;
    }

    public void setFolder_id(String folder_id) {
        this.folder_id = folder_id;
    }

    private String id;
    private String image_url;
    private String json_url;
    private int folder_pillcnt;
    private double[][] predictions;
    private double[][] pred_boxes;
    private PillModel image_id;
    private int width;
    private int height;
    private String time;
    private String pillname;

    public String getPillname() {
        return pillname;
    }

    public void setPillname(String pillname) {
        this.pillname = pillname;
    }

    private int total_image = 0;

    public int getTotal_image() {
        return total_image;
    }

    public void setTotal_image(int total_image) {
        this.total_image = total_image;
    }

    private int total_pill = 0;

    public int getTotal_pill() {
        return total_pill;
    }

    public void setTotal_pill(int total_pill) {
        this.total_pill = total_pill;
    }

    private int cnt;

    private boolean isSelect;

    private ArrayList<PillModel> images;

    public ArrayList<PillModel> getImages() {
        return images;
    }

    public void setImages(ArrayList<PillModel> images) {
        this.images = images;
    }

    private ArrayList<PillModel> image_size;
    private ArrayList<PillModel> data;
    private ArrayList<PillModel> searched_data;

    public ArrayList<PillModel> getSearched_data() {
        return searched_data;
    }

    public void setSearched_data(ArrayList<PillModel> searched_data) {
        this.searched_data = searched_data;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public int getFolder_pillcnt() {
        return folder_pillcnt;
    }

    public void setFolder_pillcnt(int folder_pillcnt) {
        this.folder_pillcnt = folder_pillcnt;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double[][] getPred_boxes() {
        return pred_boxes;
    }

    public void setPred_boxes(double[][] pred_boxes) {
        this.pred_boxes = pred_boxes;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public PillModel getImage_id() {
        return image_id;
    }

    public void setImage_id(PillModel image_id) {
        this.image_id = image_id;
    }

    public ArrayList<PillModel> getData() {
        return data;
    }

    public void setData(ArrayList<PillModel> data) {
        this.data = data;
    }

    public double[][] getPredictions() {
        return predictions;
    }

    public void setPredictions(double[][] predictions) {
        this.predictions = predictions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJson_url() {
        return json_url;
    }

    public void setJson_url(String json_url) {
        this.json_url = json_url;
    }

    public ArrayList<PillModel> getImage_size() {
        return image_size;
    }

    public void setImage_size(ArrayList<PillModel> image_size) {
        this.image_size = image_size;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
