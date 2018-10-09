package nbsix.com.duskycvre.Adapter;

import android.graphics.Bitmap;

import java.util.List;
import nbsix.com.duskycvre.Design.imagecoverflow.CoverFlowAdapter;

/**
 * Name: BannerCoverFlowAdapter
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //TODO
 * Date: 2017-07-06 17:33
 */

public class BannerCoverFlowAdapter extends CoverFlowAdapter {

   private List<Bitmap> bitmaps;

    public BannerCoverFlowAdapter() {
    }

    public void setBitmapList(List<Bitmap> bitmapList){
        bitmaps=bitmapList;
        switch (bitmaps.size()){
            case 0:
                break;
            case 1:
                //保证bitmapList.size()>=3
                bitmaps.add(bitmaps.get(0));
                bitmaps.add(bitmaps.get(0));
                curMode = MODE_SINGLE;
                break;
            case 2:
                bitmaps.add(bitmaps.get(0));
                curMode = MODE_DOUBLE;
                break;
            default:
                curMode = MODE_MUTIL;
                break;
        }
    }


    @Override
    public int getCount() {
        return bitmaps.size();
    }

    @Override
    public Bitmap getImage(int position) {
        return bitmaps.get(position % bitmaps.size());
    }

}
