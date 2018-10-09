package nbsix.com.duskycvre.Entity.HomePage;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * Name: EssayBean
 * Author: Dusky
 * QQ: 1042932843
 * Comment: 文章bean
 * Date: 2017-05-04 12:18
 */

public class EssayBean {
    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    @NonNull
    public String getImg() {
        return img;
    }

    @NonNull
    public String getPublisher() {
        return publisher;
    }

    @NonNull
    public String getTime() {
        return time;
    }

    @NonNull
    public String getContent() {
        return content;
    }

    @NonNull
    public String getLayoutstyle() {
        return layoutstyle;
    }

    @NonNull
    public List<EssayTag> getTags() {
        return tags;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    public void setImg(@NonNull String img) {
        this.img = img;
    }

    public void setPublisher(@NonNull String publisher) {
        this.publisher = publisher;
    }

    public void setTime(@NonNull String time) {
        this.time = time;
    }

    public void setContent(@NonNull String content) {
        this.content = content;
    }

    @NonNull
    private String id;

    @NonNull
    private String title;
    @NonNull
    private String img;
    @NonNull
    private String publisher;
    @NonNull
    private String time;
    @NonNull
    private String content;
    @NonNull
    private String layoutstyle;
    @NonNull
    private List<EssayTag> tags;


    public void setLayoutstyle(@NonNull String layoutstyle) {
        this.layoutstyle = layoutstyle;
    }

    public void setTags(@NonNull List<EssayTag> tags) {
        this.tags = tags;
    }

}
