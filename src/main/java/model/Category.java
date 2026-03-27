package model;

import utils.CenterFormat;

public class Category {
    private String cate_id;
    private String cate_name;
    private String description;
    private boolean status;

    public Category() {
    }

    public Category(String cate_id, String cate_name, String description) {
        this.cate_id = cate_id;
        this.cate_name = cate_name;
        this.description = description;
        this.status = true; // tạo mới nên danh mục còn (chưa xóa)
    }

    public String getCate_id() {
        return cate_id;
    }

    public void setCate_id(String cate_id) {
        this.cate_id = cate_id;
    }

    public String getCate_name() {
        return cate_name;
    }

    public void setCate_name(String cate_name) {
        this.cate_name = cate_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getStatus() {
        return this.status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void displayInforCate (){
        if(status){
            System.out.printf("| %s | %s | %s |\n", CenterFormat.center(cate_id, 12), CenterFormat.center(cate_name, 30), CenterFormat.center(description, 57));
        }
    }
}
