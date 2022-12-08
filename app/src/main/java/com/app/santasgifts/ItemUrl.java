package com.app.santasgifts;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "ItemUrl")
public class ItemUrl {

    @Id(autoincrement = true)
    private Long id;

    @Property(nameInDb = "url")
    private String url;

    @Generated(hash = 1172362881)
    public ItemUrl(Long id, String url) {
        this.id = id;
        this.url = url;
    }

    @Generated(hash = 1951738348)
    public ItemUrl() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
