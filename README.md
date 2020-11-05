# ReplaceByJava

Javaによる置換ツール。

## 実行

- フォーマットファイルを用意する。
- フォーマットファイルの中に{replace_1}, {replace_2}, ... を設定しておくと、引数の値に置換する。

```
java Replace <フォーマットファイル> hoge piyo fuga
```

```
mvn clean compile exec:java -Dexec.mainClass="tool.Replace" -Dexec.args="'./sample.txt' 'hoge' 'piyo'"
```

## 備考

なんでgit bashはこれがうまく動かないんでしょうね？

import.txt
```
hogehoge
piyopiyo
fugafuga
```

sed_test.sh
```
#!/bin/bash

export before=hogehoge
export after=OK

cat import.txt | \
  sed "s/$before/$after/g"
```

sed_test.sh 実行結果
```
OK
piyopiyo
fugafuga
```
