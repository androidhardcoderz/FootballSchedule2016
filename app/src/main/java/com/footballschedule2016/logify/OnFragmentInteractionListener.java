package com.footballschedule2016.logify;

/**
 * Created by Scott on 2/10/2016.
 */

import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * This interface must be implemented by activities that contain this
 * fragment to allow an interaction in this fragment to be communicated
 * to the activity and potentially other fragments contained in that
 * activity.
 * <p/>
 * See the Android Training lesson <a href=
 * "http://developer.android.com/training/basics/fragments/communicating.html"
 * >Communicating with Other Fragments</a> for more information.
 */
public interface OnFragmentInteractionListener {
    // TODO: Update argument type and name
    void onFragmentInteraction();
    void onChangeFragmentCondition(Fragment fragment);
    void onNewActivity(Intent intent);
}
