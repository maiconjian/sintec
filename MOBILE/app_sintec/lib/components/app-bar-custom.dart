import 'package:flutter/material.dart';

class AppBarCustom extends AppBar {
  String routePrevious;
  BuildContext context;
  dynamic argument;
  AppBarCustom(
      {super.title,
      required this.routePrevious,
      required this.context,
      this.argument})
      : super(
            leading: InkWell(
                onTap: () => {
                      Navigator.pushNamed(context, routePrevious,arguments: argument),
                    },
                child: Icon(Icons.arrow_back_ios)),
            backgroundColor: Color.fromRGBO(63, 81, 191, 1)
  );
}
