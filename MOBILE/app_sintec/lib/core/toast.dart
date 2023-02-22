import 'package:flutter/material.dart';
import 'package:fluttertoast/fluttertoast.dart';

class ToastMesage{

  static void showToastError(String msg){
    Fluttertoast.showToast(
      msg:msg,
      fontSize: 18,
      gravity: ToastGravity.BOTTOM,
      backgroundColor: Colors.red,
      textColor: Colors.white,
      toastLength: Toast.LENGTH_LONG,
    );
  }

  static void showToastSucess(String msg){
    Fluttertoast.showToast(
      msg:msg,
      fontSize: 18,
      gravity: ToastGravity.BOTTOM,
      backgroundColor: Colors.green,
      textColor: Colors.white,
      toastLength: Toast.LENGTH_LONG,
    );
  }
}