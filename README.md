# `piotr-yuxuan/moving-average-clustering`

![](./doc/social-media-preview.jpg)

[![Clojars badge](https://img.shields.io/clojars/v/com.github.piotr-yuxuan/moving-average-clustering.svg)](https://clojars.org/com.github.piotr-yuxuan/moving-average-clustering)
[![cljdoc badge](https://cljdoc.org/badge/com.github.piotr-yuxuan/moving-average-clustering)](https://cljdoc.org/d/com.github.piotr-yuxuan/moving-average-clustering/CURRENT)
[![GitHub license](https://img.shields.io/github/license/piotr-yuxuan/moving-average-clustering)](https://github.com/piotr-yuxuan/moving-average-clustering/blob/main/LICENSE)
[![GitHub issues](https://img.shields.io/github/issues/piotr-yuxuan/moving-average-clustering)](https://github.com/piotr-yuxuan/moving-average-clustering/issues)

# What it offers

Online algorithm to compute moving average clustering with immutable
data srtructure.

# Maturity and evolution

It's still very young, and currently only a playground / mathless
aftermath.

# Getting started

At first, whatever the cost, the space complexity or time complexity,
we want an answer for small data sets. Then we can get a better
understanding of the problem, give a proper problem statement, and
compare the results of later implementations with that of the first so
as to ensure correctness.

Don't repeat it at home guys. Don't put this code in production as of
yet or you could be fired. This man is a professional who knows what
he's doing.

We have queries on some assets, identified by integers.

``` clojure
(def q1 #{1 2 4})
(def q2 #{1 2 4 6})
(def q3 #{1 8})
(def q4 #{1 2 4 8})
(def q5 #{1 2 3 5 7 8})
(def q6 #{2 4 6})
(def q7 #{2 3 5 6 7})
(def q8 #{3 6})
```

Instead of retrieving data for each asset on each query, we want to
use clustering to group assets together. For example, if assets `1`,
`2`, `3`, `4`, and `5` are grouped together, it costs only one IO to
read all of them at once. As a result, query `q5` only three IO
instead of costing eight, which is a significant improvement. On
larger queries spanning accross thousands of assets, it could be a
life saver.

I see two different orthogonal approaches, when :

- If we choose a max edit distance, then we want to find a
  (non-unique) smallest set of asset clusters able to recreate all the
  queries we have seen with a number of editions lesser than the
  threshold;
- Otherwise we choose a maximum number of clusters and we want to tune
  them to that they do their best to resemble any query with as few
  editions as possible.

I am of the opinion that the first choice is the most
interesting. Memory is cheap, disk space is cheap, and we are more
than happy to throw money at (possibly mem-mapped) disk space to
reduce query latency.

## Example

For the eight queries above, the min / average / max numbers of assets
we have to fetch are: 2 / ~3.625 / 8.

If we tolerate 2 editions, that is to 2+1 data retrieval, we may use
this clustering:

``` clojure
(require '[piotr-yuxuan.moving-average-clustering.main :as main])

(main/nearby-synthetic-queries 2 queries)
=> #{#{6 3}
     #{7 1 3 2 5 8}
     #{1 8}
     #{4 6 2}
     #{7 6 3 2 5}}
```

This is already better than the average for a cost of storing five
more "assets". If we can find an optimised way to only focus on the
clusters we need and we tolerate some editions, the additional disk
space cost will probably be completely worth the speed-up for larger
queries spanning across 10k+ assets.

# References

- Learning Clojure: practicalli.github.io/
