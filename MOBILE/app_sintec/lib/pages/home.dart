import 'package:app_sintec/components/app-bar-custom.dart';
import 'package:app_sintec/shared/util/gps.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:app_sintec/components/menu_item.dart' as menuItem;
import 'package:app_sintec/shared/data/dummy_data.dart';

class Home extends StatelessWidget {
  const Home({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar:AppBarCustom(title: Text("Home"), routePrevious: "/login", context: context),
      body: GridView(
          padding: EdgeInsets.all(18),
          gridDelegate: SliverGridDelegateWithMaxCrossAxisExtent(
              maxCrossAxisExtent: 200,
              childAspectRatio: 5 / 2,
              crossAxisSpacing: 10,
              mainAxisSpacing: 12
          ),
        children: dummyMenus.map((e) => 
           menuItem.MenuItem(e)
        ).toList(),
      )
    );
  }
}
