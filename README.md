# ReplaceByJava

Javaによる置換ツール。

## 実行方法

### フォーマットファイルを用意する

置換マップとしてJSONを使用する。

``` json : replace.json
[
  { "search": "{replace_1}" , "replace":"hogehoge" },
  { "search": "{replace_2}" , "replace":"piyopiyo" }
]
```

### 実行

``` bash
from=./testdata/from
to=./testdata/to
replacemap=./replace.json
./mvnw clean compile exec:java -Dexec.mainClass="Replace" -Dexec.args="'${from}' '${to}' '${replacemap}'"
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
