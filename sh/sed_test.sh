#!/bin/bash

export before=hogehoge
export after=OK

cat import.txt | \
  sed "s/$before/$after/g"
