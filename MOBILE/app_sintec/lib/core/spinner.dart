import 'package:flutter/material.dart';
import 'package:flutter_spinkit/flutter_spinkit.dart';

class Spinner{

  static Widget showSpinner(){
    return Container(
          width: double.infinity,
          // decoration: BoxDecoration(color:Color.fromRGBO(63, 81, 191, 1)),
          // child: SpinKitRotatingCircle(
          //   color: Color.fromRGBO(63, 81, 191, 1),
          //   size: 50.0,
          // )
        child: SpinKitThreeBounce(
          color: Color.fromRGBO(63, 81, 191, 1),
          size: 50.0,
        )
    );
  }
}