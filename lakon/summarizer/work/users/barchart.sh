#!/bin/bash

mkdir -p charts
for file in `ls sentence-selection-art-*`; do

gnuplot << EOF

set title "$file"
set style fill solid 0.25 noborder
set style histogram
set style data histograms
set boxwidth 0.9 absolute

set key on

set xrange [-0.5:]

set xtics border nomirror 5
set ytics border nomirror
show tics

set terminal postscript color enhanced dashed

set xlabel "Sentence number"
set ylabel "Number of selections"

set output "charts/$file.eps"
plot '$file' using 1:2 with boxes title ""

EOF

done