package com.sh.study.udacitynano.planner.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

/**
 * Categories
 *
 *
 * @author Sławomir Hagiel
 * @version 1.0
 * @since 2018-07-13
 */
@Entity(tableName = "category")
public class CategoryEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    @ColumnInfo(name = "parent_id")
    private int parentId;
/*
    @ColumnInfo(name = "updated_at")
    private Date updatedAt;
*/

    @Ignore
//    public CategoryEntity(String name, int parentId, Date updatedAt) {
      public CategoryEntity(String name, int parentId) {
        this.name = name;
        this.parentId = parentId;
//        this.updatedAt = updatedAt;
    }

//    public CategoryEntity(int id, String name, int parentId, Date updatedAt) {
    public CategoryEntity(int id, String name, int parentId) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
//        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

/*
    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
*/
}
