package jp.ac.titech.itpro.sdl.rssreader;

/**
 * Created by daron on 2017/07/06.
 */

public class Item {
    private CharSequence mTitle;
    private CharSequence mDescription;
    private CharSequence mLink;

    public Item() {
        mTitle = "";
        mDescription = "";
        mLink = "";
    }

    public CharSequence getDescription() {
        return mDescription;
    }

    public void setDescription(CharSequence description) {
        mDescription = description;
    }

    public CharSequence getTitle() {
        return mTitle;
    }

    public void setTitle(CharSequence title) {
        mTitle = title;
    }

    public void setLink(CharSequence link) {
        mLink = link;
    }

    public CharSequence getLink() { return mLink; }
}
