public class NamingRules {

  public static void main(String[] args) {

    int java;//valid
    int _java;//valid
    int $java;//valid
    int _$java;//valid
    int __$$java;//valid

    int 1java;//invalid
    int &java;//invalid
    int @java;//invalid
    int java@java;//invalid
    int public;//invalid

    int Public;//valid public is different from Public in Java -> Java is case-sensetive
    int $public;//valid
    int _public;//valid

  }

  void java(){}//valid
  void _java(){}//valid
  void $java(){}//valid
  void _$java(){}//valid
  void __$$java_$(){}//valid
  void Java(){}//valid -> not reccommended

  void 1java(){}//invalid
  void &java(){}//invalid
  void @java(){}//invalid
  void public(){}//invalid

  void Public(){}//valid
  void $public(){}//valid
  void _public(){}//valid

}
