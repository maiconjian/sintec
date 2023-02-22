import 'package:device_information/device_information.dart';

class InfoDevice{

  static Future<Map> getInfo() async {
    return <String,dynamic>{
        'modelo': await DeviceInformation.deviceModel,
        'marca':  await DeviceInformation.deviceManufacturer,
        'nomeDispositivo': await DeviceInformation.deviceName,
    };
  }

}

