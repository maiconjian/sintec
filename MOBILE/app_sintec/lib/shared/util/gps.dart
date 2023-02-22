import 'package:flutter/services.dart';
import 'package:location/location.dart';

class Gps{

  Location location = new Location();
  late bool _serviceEnabled;
  late PermissionStatus _permissionGranted;
  late LocationData _locationData;


  Future<void> getVerificarPermissoes() async{
    _serviceEnabled = await location.serviceEnabled();
    if (!_serviceEnabled) {
      _serviceEnabled = await location.requestService();
      if (!_serviceEnabled) {
        SystemNavigator.pop();
        return;
      }
    }
    _permissionGranted = await location.hasPermission();
    if (_permissionGranted == PermissionStatus.denied) {
      _permissionGranted = await location.requestPermission();
      if (_permissionGranted != PermissionStatus.granted) {
        return;
      }
    }
    _locationData = await location.getLocation();
  }

  String getLongitude(){
    return _locationData.longitude.toString();
  }

  String getLatitude(){
    return _locationData.latitude.toString();
  }

  // void verificaGps() {
  //  getVerificarPermissoes().then((value) => {
  //    if(!_serviceEnabled && _permissionGranted == PermissionStatus.denied){
  //      print("ligar gps"),
  //    }
  //  });
  // }

}