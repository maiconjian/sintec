import 'package:app_sintec/shared/data/dummy_data.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class MenuItem extends StatelessWidget {
  final Menu menu;

  const MenuItem(this.menu, {Key? key}) : super(key: key);

  void _selectRoute(BuildContext context){
    WidgetsBinding.instance.addPostFrameCallback((_) {
      Navigator.pushNamed(context,menu.route);
      //  pegar argumento na outra classe
      //  Modaloute.of(context).settings.arguments
    });
  }

  @override
  Widget build(BuildContext context) {
    return InkWell(
      onTap: () => _selectRoute(context),
      borderRadius: BorderRadius.circular(12),
      splashColor: Theme.of(context).primaryColor,
      child: Container(
        child: Row(
          children: [
            Padding(padding: EdgeInsets.only(left: 5), child: menu.icon),
            Padding(
              padding: EdgeInsets.only(left: 5),
              child: Text(
                menu.titulo,
                style: TextStyle(
                  color: Colors.white,
                  fontWeight: FontWeight.w600,
                ),
              ),
            )
          ],
        ),
        decoration: BoxDecoration(
            borderRadius: BorderRadius.circular(12),
            gradient: LinearGradient(colors: [
              menu.cor.withOpacity(0.5),
              menu.cor,
            ], begin: Alignment.topLeft, end: Alignment.bottomRight)),
      ),
    );
  }
}
