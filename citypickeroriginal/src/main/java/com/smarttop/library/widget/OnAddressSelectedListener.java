package com.smarttop.library.widget;


import com.smarttop.library.bean.City;
import com.smarttop.library.bean.County;
import com.smarttop.library.bean.Province;
import com.smarttop.library.bean.Street;

public interface OnAddressSelectedListener {
    void onAddressSelected(Province province, City city, County county, Street street);
//    void onAddressSelected(Province province, City city, County county);
//    void onAddressSelected(Province province, City city);

}
