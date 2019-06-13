# WSO2 API Microgateway Performance Test Results

During each release, we execute various automated performance test scenarios and publish the results.

| Test Scenarios | Description |
| --- | --- |
| Microgateway-Passthrough-OAuth2 | A secured API, which directly invokes the backend through Microgateway using OAuth2 tokens |
| Microgateway-Passthrough-JWT | A secured API, which directly invokes the backend through Microgateway using JWT tokens |

Our test client is [Apache JMeter](https://jmeter.apache.org/index.html). We test each scenario for a fixed duration of
time. We split the test results into warmup and measurement parts and use the measurement part to compute the
performance metrics.

Test scenarios use a [Netty](https://netty.io/) based back-end service which echoes back any request
posted to it after a specified period of time.

We run the performance tests under different numbers of concurrent users, message sizes (payloads) and back-end service
delays.

The main performance metrics:

1. **Throughput**: The number of requests that the WSO2 API Microgateway processes during a specific time interval (e.g. per second).
2. **Response Time**: The end-to-end latency for an operation of invoking an API. The complete distribution of response times was recorded.

In addition to the above metrics, we measure the load average and several memory-related metrics.

The following are the test parameters.

| Test Parameter | Description | Values |
| --- | --- | --- |
| Scenario Name | The name of the test scenario. | Refer to the above table. |
| Heap Size | The amount of memory allocated to the application | 4G |
| Concurrent Users | The number of users accessing the application at the same time. | 50, 100, 200, 300, 500, 1000 |
| Message Size (Bytes) | The request payload size in Bytes. | 50, 1024, 10240 |
| Back-end Delay (ms) | The delay added by the back-end service. | 0, 30, 500, 1000 |

The duration of each test is **1200 seconds**. The warm-up period is **300 seconds**.
The measurement results are collected after the warm-up period.

A [**m5.xlarge** Amazon EC2 instance](https://aws.amazon.com/ec2/instance-types/) was used to install WSO2 API Microgateway.

The following are the measurements collected from each performance test conducted for a given combination of
test parameters.

| Measurement | Description |
| --- | --- |
| Error % | Percentage of requests with errors |
| Average Response Time (ms) | The average response time of a set of results |
| Standard Deviation of Response Time (ms) | The “Standard Deviation” of the response time. |
| 99th Percentile of Response Time (ms) | 99% of the requests took no more than this time. The remaining samples took at least as long as this |
| Throughput (Requests/sec) | The throughput measured in requests per second. |
| Average Memory Footprint After Full GC (M) | The average memory consumed by the application after a full garbage collection event. |

The following is the summary of performance test results collected for the measurement period.

|  Scenario Name | Heap Size | Concurrent Users | Message Size (Bytes) | Back-end Service Delay (ms) | Error % | Throughput (Requests/sec) | Average Response Time (ms) | Standard Deviation of Response Time (ms) | 99th Percentile of Response Time (ms) | WSO2 API Microgateway GC Throughput (%) | Average WSO2 API Microgateway Memory Footprint After Full GC (M) |
|---|---:|---:|---:|---:|---:|---:|---:|---:|---:|---:|---:|
|  Microgateway-Passthrough-JWT | 4G | 50 | 50 | 0 | 0 | 1717.26 | 29.02 | 28.57 | 114 | N/A | N/A |
|  Microgateway-Passthrough-JWT | 4G | 50 | 50 | 30 | 0 | 1509.36 | 33.05 | 7.49 | 82 | N/A | N/A |
|  Microgateway-Passthrough-JWT | 4G | 50 | 50 | 500 | 0 | 99.48 | 502.96 | 3.59 | 515 | N/A | N/A |
|  Microgateway-Passthrough-JWT | 4G | 50 | 50 | 1000 | 0 | 49.84 | 1002.58 | 3.58 | 1015 | N/A | N/A |
|  Microgateway-Passthrough-JWT | 4G | 50 | 1024 | 0 | 0 | 1668.44 | 29.87 | 29.1 | 117 | N/A | N/A |
|  Microgateway-Passthrough-JWT | 4G | 50 | 1024 | 30 | 0 | 1501.06 | 33.22 | 7.79 | 84 | N/A | N/A |
|  Microgateway-Passthrough-JWT | 4G | 50 | 1024 | 500 | 0 | 99.39 | 503.2 | 4.29 | 515 | N/A | N/A |
|  Microgateway-Passthrough-JWT | 4G | 50 | 1024 | 1000 | 0 | 49.83 | 1002.42 | 3.86 | 1007 | N/A | N/A |
|  Microgateway-Passthrough-JWT | 4G | 50 | 10240 | 0 | 0 | 1258.96 | 39.59 | 30.51 | 124 | N/A | N/A |
|  Microgateway-Passthrough-JWT | 4G | 50 | 10240 | 30 | 0 | 1233.98 | 40.41 | 10.78 | 96 | N/A | N/A |
|  Microgateway-Passthrough-JWT | 4G | 50 | 10240 | 500 | 0 | 99.41 | 503.38 | 3.24 | 515 | N/A | N/A |
|  Microgateway-Passthrough-JWT | 4G | 50 | 10240 | 1000 | 0 | 49.81 | 1002.63 | 3.75 | 1007 | N/A | N/A |
|  Microgateway-Passthrough-JWT | 4G | 100 | 50 | 0 | 0 | 1733.13 | 57.6 | 40.11 | 185 | N/A | N/A |
|  Microgateway-Passthrough-JWT | 4G | 100 | 50 | 30 | 0 | 1778.49 | 56.12 | 18.88 | 129 | N/A | N/A |
|  Microgateway-Passthrough-JWT | 4G | 100 | 50 | 500 | 0 | 198.77 | 503.08 | 3.62 | 519 | N/A | N/A |
|  Microgateway-Passthrough-JWT | 4G | 100 | 50 | 1000 | 0 | 99.67 | 1002.34 | 3.08 | 1007 | N/A | N/A |
|  Microgateway-Passthrough-JWT | 4G | 100 | 1024 | 0 | 0 | 1711.26 | 58.33 | 38.54 | 179 | N/A | N/A |
|  Microgateway-Passthrough-JWT | 4G | 100 | 1024 | 30 | 0 | 1759.71 | 56.71 | 19 | 130 | N/A | N/A |
|  Microgateway-Passthrough-JWT | 4G | 100 | 1024 | 500 | 0 | 198.76 | 503.38 | 4.74 | 523 | N/A | N/A |
|  Microgateway-Passthrough-JWT | 4G | 100 | 1024 | 1000 | 0 | 99.66 | 1002.84 | 4.44 | 1015 | N/A | N/A |
|  Microgateway-Passthrough-JWT | 4G | 100 | 10240 | 0 | 0 | 1242.54 | 80.33 | 41.46 | 199 | N/A | N/A |
|  Microgateway-Passthrough-JWT | 4G | 100 | 10240 | 30 | 0 | 1285.43 | 77.63 | 25.65 | 158 | N/A | N/A |
|  Microgateway-Passthrough-JWT | 4G | 100 | 10240 | 500 | 0 | 198.7 | 503.55 | 3.33 | 519 | N/A | N/A |
|  Microgateway-Passthrough-JWT | 4G | 100 | 10240 | 1000 | 0 | 99.61 | 1002.88 | 4.09 | 1019 | N/A | N/A |
|  Microgateway-Passthrough-JWT | 4G | 200 | 50 | 0 | 0 | 1745.36 | 114.46 | 53.32 | 287 | N/A | N/A |
|  Microgateway-Passthrough-JWT | 4G | 200 | 50 | 30 | 0 | 1796.93 | 111.16 | 35.64 | 213 | N/A | N/A |
|  Microgateway-Passthrough-JWT | 4G | 200 | 50 | 500 | 0 | 397.49 | 503.4 | 5.25 | 531 | N/A | N/A |
|  Microgateway-Passthrough-JWT | 4G | 200 | 50 | 1000 | 0 | 199.21 | 1003.51 | 5.33 | 1031 | N/A | N/A |
|  Microgateway-Passthrough-JWT | 4G | 200 | 1024 | 0 | 0 | 1668.46 | 119.77 | 56.43 | 297 | N/A | N/A |
|  Microgateway-Passthrough-JWT | 4G | 200 | 1024 | 30 | 0 | 1701.78 | 117.38 | 37.23 | 225 | N/A | N/A |
|  Microgateway-Passthrough-JWT | 4G | 200 | 1024 | 500 | 0 | 397.25 | 503.52 | 4.78 | 527 | N/A | N/A |
|  Microgateway-Passthrough-JWT | 4G | 200 | 1024 | 1000 | 0 | 199.23 | 1003.4 | 5.09 | 1031 | N/A | N/A |
|  Microgateway-Passthrough-JWT | 4G | 200 | 10240 | 0 | 0 | 1252.46 | 159.53 | 68.16 | 377 | N/A | N/A |
|  Microgateway-Passthrough-JWT | 4G | 200 | 10240 | 30 | 0 | 1277.37 | 156.38 | 52.8 | 305 | N/A | N/A |
|  Microgateway-Passthrough-JWT | 4G | 200 | 10240 | 500 | 0 | 396.94 | 503.98 | 4.62 | 531 | N/A | N/A |
|  Microgateway-Passthrough-JWT | 4G | 200 | 10240 | 1000 | 0 | 199.17 | 1003.72 | 5.14 | 1031 | N/A | N/A |
|  Microgateway-Passthrough-JWT | 4G | 300 | 50 | 0 | 0 | 1781.24 | 168.31 | 71.01 | 391 | N/A | N/A |
|  Microgateway-Passthrough-JWT | 4G | 300 | 50 | 30 | 0 | 1771.19 | 169.24 | 59.74 | 335 | N/A | N/A |
|  Microgateway-Passthrough-JWT | 4G | 300 | 50 | 500 | 0 | 596.26 | 503.16 | 5.64 | 535 | N/A | N/A |
|  Microgateway-Passthrough-JWT | 4G | 300 | 50 | 1000 | 0 | 298.74 | 1003.13 | 5.06 | 1031 | N/A | N/A |
|  Microgateway-Passthrough-JWT | 4G | 300 | 1024 | 0 | 0 | 1673.73 | 179.15 | 74.17 | 403 | N/A | N/A |
|  Microgateway-Passthrough-JWT | 4G | 300 | 1024 | 30 | 0 | 1706.5 | 175.62 | 61.99 | 371 | N/A | N/A |
|  Microgateway-Passthrough-JWT | 4G | 300 | 1024 | 500 | 0 | 596.37 | 503.19 | 5.13 | 531 | N/A | N/A |
|  Microgateway-Passthrough-JWT | 4G | 300 | 1024 | 1000 | 0 | 298.95 | 1002.83 | 4.32 | 1023 | N/A | N/A |
|  Microgateway-Passthrough-JWT | 4G | 300 | 10240 | 0 | 0 | 1254.43 | 239.11 | 96.53 | 523 | N/A | N/A |
|  Microgateway-Passthrough-JWT | 4G | 300 | 10240 | 30 | 0 | 1281.73 | 233.99 | 79.15 | 475 | N/A | N/A |
|  Microgateway-Passthrough-JWT | 4G | 300 | 10240 | 500 | 0 | 595.29 | 504.15 | 5.69 | 535 | N/A | N/A |
|  Microgateway-Passthrough-JWT | 4G | 300 | 10240 | 1000 | 0 | 298.87 | 1002.99 | 4.53 | 1031 | N/A | N/A |
|  Microgateway-Passthrough-JWT | 4G | 500 | 50 | 0 | 0 | 1731.62 | 288.81 | 102.47 | 599 | N/A | N/A |
|  Microgateway-Passthrough-JWT | 4G | 500 | 50 | 30 | 0 | 1772.19 | 282.18 | 89.92 | 563 | N/A | N/A |
|  Microgateway-Passthrough-JWT | 4G | 500 | 50 | 500 | 0 | 986.77 | 506.48 | 12.12 | 571 | N/A | N/A |
|  Microgateway-Passthrough-JWT | 4G | 500 | 50 | 1000 | 0 | 496.31 | 1006.15 | 10.96 | 1063 | N/A | N/A |
|  Microgateway-Passthrough-JWT | 4G | 500 | 1024 | 0 | 0 | 1685.88 | 296.6 | 108.94 | 611 | N/A | N/A |
|  Microgateway-Passthrough-JWT | 4G | 500 | 1024 | 30 | 0 | 1713.73 | 291.84 | 94 | 583 | N/A | N/A |
|  Microgateway-Passthrough-JWT | 4G | 500 | 1024 | 500 | 0 | 988.86 | 505.66 | 11.01 | 567 | N/A | N/A |
|  Microgateway-Passthrough-JWT | 4G | 500 | 1024 | 1000 | 0 | 496.18 | 1006.46 | 11.03 | 1063 | N/A | N/A |
|  Microgateway-Passthrough-JWT | 4G | 500 | 10240 | 0 | 0 | 1233.49 | 405.43 | 148.13 | 827 | N/A | N/A |
|  Microgateway-Passthrough-JWT | 4G | 500 | 10240 | 30 | 0 | 1264.66 | 395.42 | 130.79 | 787 | N/A | N/A |
|  Microgateway-Passthrough-JWT | 4G | 500 | 10240 | 500 | 0 | 986.61 | 506.7 | 11.06 | 567 | N/A | N/A |
|  Microgateway-Passthrough-JWT | 4G | 500 | 10240 | 1000 | 0 | 497.74 | 1003.47 | 6.14 | 1039 | N/A | N/A |
|  Microgateway-Passthrough-JWT | 4G | 1000 | 50 | 0 | 0 | 1712.33 | 584.05 | 184.95 | 1111 | N/A | N/A |
|  Microgateway-Passthrough-JWT | 4G | 1000 | 50 | 30 | 0 | 1722.24 | 580.57 | 165.74 | 1079 | N/A | N/A |
|  Microgateway-Passthrough-JWT | 4G | 1000 | 50 | 500 | 0 | 1724.74 | 579.68 | 45.94 | 703 | N/A | N/A |
|  Microgateway-Passthrough-JWT | 4G | 1000 | 50 | 1000 | 0 | 993.5 | 1005.25 | 12.41 | 1087 | N/A | N/A |
|  Microgateway-Passthrough-JWT | 4G | 1000 | 1024 | 0 | 0 | 1666.01 | 600.25 | 194.11 | 1143 | N/A | N/A |
|  Microgateway-Passthrough-JWT | 4G | 1000 | 1024 | 30 | 0 | 1689.14 | 592.03 | 176.79 | 1103 | N/A | N/A |
|  Microgateway-Passthrough-JWT | 4G | 1000 | 1024 | 500 | 0 | 1662.96 | 601.21 | 56.7 | 763 | N/A | N/A |
|  Microgateway-Passthrough-JWT | 4G | 1000 | 1024 | 1000 | 0 | 993.24 | 1005.51 | 12.82 | 1087 | N/A | N/A |
|  Microgateway-Passthrough-JWT | 4G | 1000 | 10240 | 0 | 0 | 1230.71 | 812.03 | 264.36 | 1559 | N/A | N/A |
|  Microgateway-Passthrough-JWT | 4G | 1000 | 10240 | 30 | 0 | 1234.84 | 809.19 | 252.43 | 1519 | N/A | N/A |
|  Microgateway-Passthrough-JWT | 4G | 1000 | 10240 | 500 | 0 | 1232.3 | 810.97 | 127.64 | 1167 | N/A | N/A |
|  Microgateway-Passthrough-JWT | 4G | 1000 | 10240 | 1000 | 0 | 988.74 | 1009.97 | 16.63 | 1095 | N/A | N/A |
|  Microgateway-Passthrough-OAuth2 | 4G | 50 | 50 | 0 | 0 | 2003.71 | 24.88 | 27.08 | 107 | N/A | N/A |
|  Microgateway-Passthrough-OAuth2 | 4G | 50 | 50 | 30 | 0 | 1521.91 | 32.78 | 8.23 | 89 | N/A | N/A |
|  Microgateway-Passthrough-OAuth2 | 4G | 50 | 50 | 500 | 0 | 99.53 | 502.7 | 4.13 | 515 | N/A | N/A |
|  Microgateway-Passthrough-OAuth2 | 4G | 50 | 50 | 1000 | 0 | 49.76 | 1003.39 | 5.82 | 1031 | N/A | N/A |
|  Microgateway-Passthrough-OAuth2 | 4G | 50 | 1024 | 0 | 0 | 1969.54 | 25.31 | 27.36 | 107 | N/A | N/A |
|  Microgateway-Passthrough-OAuth2 | 4G | 50 | 1024 | 30 | 0 | 1519.18 | 32.84 | 8.09 | 88 | N/A | N/A |
|  Microgateway-Passthrough-OAuth2 | 4G | 50 | 1024 | 500 | 0 | 99.48 | 502.96 | 3.8 | 511 | N/A | N/A |
|  Microgateway-Passthrough-OAuth2 | 4G | 50 | 1024 | 1000 | 0 | 49.84 | 1002.86 | 4.32 | 1019 | N/A | N/A |
|  Microgateway-Passthrough-OAuth2 | 4G | 50 | 10240 | 0 | 0 | 1393.61 | 35.77 | 29.88 | 125 | N/A | N/A |
|  Microgateway-Passthrough-OAuth2 | 4G | 50 | 10240 | 30 | 0 | 1382.23 | 36.08 | 9.35 | 95 | N/A | N/A |
|  Microgateway-Passthrough-OAuth2 | 4G | 50 | 10240 | 500 | 0 | 99.42 | 503.27 | 3.96 | 515 | N/A | N/A |
|  Microgateway-Passthrough-OAuth2 | 4G | 50 | 10240 | 1000 | 0 | 49.8 | 1003.24 | 4.6 | 1031 | N/A | N/A |
|  Microgateway-Passthrough-OAuth2 | 4G | 100 | 50 | 0 | 0 | 2011.01 | 49.64 | 36.44 | 168 | N/A | N/A |
|  Microgateway-Passthrough-OAuth2 | 4G | 100 | 50 | 30 | 0 | 2032.08 | 49.12 | 16.85 | 126 | N/A | N/A |
|  Microgateway-Passthrough-OAuth2 | 4G | 100 | 50 | 500 | 0 | 198.9 | 503.05 | 4.41 | 523 | N/A | N/A |
|  Microgateway-Passthrough-OAuth2 | 4G | 100 | 50 | 1000 | 0 | 99.65 | 1002.33 | 3.76 | 1007 | N/A | N/A |
|  Microgateway-Passthrough-OAuth2 | 4G | 100 | 1024 | 0 | 0 | 1994.61 | 50.04 | 36.45 | 167 | N/A | N/A |
|  Microgateway-Passthrough-OAuth2 | 4G | 100 | 1024 | 30 | 0 | 2000.76 | 49.89 | 16.79 | 120 | N/A | N/A |
|  Microgateway-Passthrough-OAuth2 | 4G | 100 | 1024 | 500 | 0 | 198.77 | 503.19 | 4.83 | 523 | N/A | N/A |
|  Microgateway-Passthrough-OAuth2 | 4G | 100 | 1024 | 1000 | 0 | 99.67 | 1002.47 | 3.84 | 1015 | N/A | N/A |
|  Microgateway-Passthrough-OAuth2 | 4G | 100 | 10240 | 0 | 0 | 1415.38 | 70.52 | 39.01 | 188 | N/A | N/A |
|  Microgateway-Passthrough-OAuth2 | 4G | 100 | 10240 | 30 | 0 | 1413.42 | 70.62 | 25.06 | 162 | N/A | N/A |
|  Microgateway-Passthrough-OAuth2 | 4G | 100 | 10240 | 500 | 0 | 198.61 | 503.68 | 4.53 | 527 | N/A | N/A |
|  Microgateway-Passthrough-OAuth2 | 4G | 100 | 10240 | 1000 | 0 | 99.61 | 1002.56 | 3.33 | 1015 | N/A | N/A |
|  Microgateway-Passthrough-OAuth2 | 4G | 200 | 50 | 0 | 0 | 2033.35 | 98.25 | 47.97 | 250 | N/A | N/A |
|  Microgateway-Passthrough-OAuth2 | 4G | 200 | 50 | 30 | 0 | 2027.59 | 98.52 | 31.89 | 198 | N/A | N/A |
|  Microgateway-Passthrough-OAuth2 | 4G | 200 | 50 | 500 | 0 | 397.83 | 502.82 | 4.47 | 523 | N/A | N/A |
|  Microgateway-Passthrough-OAuth2 | 4G | 200 | 50 | 1000 | 0 | 199.33 | 1002.89 | 4.97 | 1023 | N/A | N/A |
|  Microgateway-Passthrough-OAuth2 | 4G | 200 | 1024 | 0 | 0 | 2039.44 | 97.96 | 46.78 | 233 | N/A | N/A |
|  Microgateway-Passthrough-OAuth2 | 4G | 200 | 1024 | 30 | 0 | 2094.91 | 95.34 | 28.93 | 193 | N/A | N/A |
|  Microgateway-Passthrough-OAuth2 | 4G | 200 | 1024 | 500 | 0 | 397.58 | 503.24 | 5.5 | 535 | N/A | N/A |
|  Microgateway-Passthrough-OAuth2 | 4G | 200 | 1024 | 1000 | 0 | 199.22 | 1003.04 | 4.6 | 1031 | N/A | N/A |
|  Microgateway-Passthrough-OAuth2 | 4G | 200 | 10240 | 0 | 0 | 1429.42 | 139.77 | 61.07 | 313 | N/A | N/A |
|  Microgateway-Passthrough-OAuth2 | 4G | 200 | 10240 | 30 | 0 | 1423.43 | 140.31 | 46.36 | 285 | N/A | N/A |
|  Microgateway-Passthrough-OAuth2 | 4G | 200 | 10240 | 500 | 0 | 397.32 | 503.73 | 5.12 | 531 | N/A | N/A |
|  Microgateway-Passthrough-OAuth2 | 4G | 200 | 10240 | 1000 | 0 | 199.11 | 1003.23 | 5.19 | 1031 | N/A | N/A |
|  Microgateway-Passthrough-OAuth2 | 4G | 300 | 50 | 0 | 0 | 2047.06 | 146.46 | 61.94 | 327 | N/A | N/A |
|  Microgateway-Passthrough-OAuth2 | 4G | 300 | 50 | 30 | 0 | 2122.25 | 141.22 | 49.03 | 289 | N/A | N/A |
|  Microgateway-Passthrough-OAuth2 | 4G | 300 | 50 | 500 | 0 | 595.65 | 503.8 | 7.44 | 547 | N/A | N/A |
|  Microgateway-Passthrough-OAuth2 | 4G | 300 | 50 | 1000 | 0 | 299 | 1003.25 | 10.74 | 1031 | N/A | N/A |
|  Microgateway-Passthrough-OAuth2 | 4G | 300 | 1024 | 0 | 0 | 1967.31 | 152.4 | 62 | 337 | N/A | N/A |
|  Microgateway-Passthrough-OAuth2 | 4G | 300 | 1024 | 30 | 0 | 2033.36 | 147.42 | 50.93 | 297 | N/A | N/A |
|  Microgateway-Passthrough-OAuth2 | 4G | 300 | 1024 | 500 | 0 | 595.4 | 503.94 | 7.09 | 543 | N/A | N/A |
|  Microgateway-Passthrough-OAuth2 | 4G | 300 | 1024 | 1000 | 0 | 299.03 | 1002.86 | 10.35 | 1019 | N/A | N/A |
|  Microgateway-Passthrough-OAuth2 | 4G | 300 | 10240 | 0 | 0 | 1379.35 | 217.48 | 86.85 | 485 | N/A | N/A |
|  Microgateway-Passthrough-OAuth2 | 4G | 300 | 10240 | 30 | 0 | 1405.84 | 213.31 | 71.22 | 409 | N/A | N/A |
|  Microgateway-Passthrough-OAuth2 | 4G | 300 | 10240 | 500 | 0 | 595.89 | 503.68 | 5.88 | 531 | N/A | N/A |
|  Microgateway-Passthrough-OAuth2 | 4G | 300 | 10240 | 1000 | 0 | 298.93 | 1002.98 | 4.49 | 1031 | N/A | N/A |
|  Microgateway-Passthrough-OAuth2 | 4G | 500 | 50 | 0 | 0 | 2059.76 | 242.74 | 87.76 | 505 | N/A | N/A |
|  Microgateway-Passthrough-OAuth2 | 4G | 500 | 50 | 30 | 0 | 2050.44 | 243.87 | 78.94 | 485 | N/A | N/A |
|  Microgateway-Passthrough-OAuth2 | 4G | 500 | 50 | 500 | 0 | 990.28 | 504.66 | 10.36 | 567 | N/A | N/A |
|  Microgateway-Passthrough-OAuth2 | 4G | 500 | 50 | 1000 | 0 | 497.6 | 1003.64 | 16.03 | 1047 | N/A | N/A |
|  Microgateway-Passthrough-OAuth2 | 4G | 500 | 1024 | 0 | 0 | 1952.69 | 256.08 | 89.15 | 515 | N/A | N/A |
|  Microgateway-Passthrough-OAuth2 | 4G | 500 | 1024 | 30 | 0 | 2010.05 | 248.77 | 80.39 | 485 | N/A | N/A |
|  Microgateway-Passthrough-OAuth2 | 4G | 500 | 1024 | 500 | 0 | 989.64 | 505.23 | 11.23 | 571 | N/A | N/A |
|  Microgateway-Passthrough-OAuth2 | 4G | 500 | 1024 | 1000 | 0 | 498.12 | 1003.25 | 6.57 | 1047 | N/A | N/A |
|  Microgateway-Passthrough-OAuth2 | 4G | 500 | 10240 | 0 | 0 | 1388.78 | 360.1 | 130.69 | 727 | N/A | N/A |
|  Microgateway-Passthrough-OAuth2 | 4G | 500 | 10240 | 30 | 0 | 1387.46 | 360.5 | 117.16 | 699 | N/A | N/A |
|  Microgateway-Passthrough-OAuth2 | 4G | 500 | 10240 | 500 | 0 | 985.59 | 507.32 | 12.66 | 575 | N/A | N/A |
|  Microgateway-Passthrough-OAuth2 | 4G | 500 | 10240 | 1000 | 0 | 497.81 | 1003.44 | 11.45 | 1039 | N/A | N/A |
|  Microgateway-Passthrough-OAuth2 | 4G | 1000 | 50 | 0 | 23.2 | 210.29 | 4559.59 | 12894.12 | 71167 | N/A | N/A |
|  Microgateway-Passthrough-OAuth2 | 4G | 1000 | 50 | 30 | 0 | 2000.23 | 499.99 | 144.49 | 923 | N/A | N/A |
|  Microgateway-Passthrough-OAuth2 | 4G | 1000 | 50 | 500 | 0 | 1870.8 | 534.42 | 31.81 | 623 | N/A | N/A |
|  Microgateway-Passthrough-OAuth2 | 4G | 1000 | 50 | 1000 | 0 | 992.2 | 1006.81 | 14.35 | 1087 | N/A | N/A |
|  Microgateway-Passthrough-OAuth2 | 4G | 1000 | 1024 | 0 | 0 | 1905.73 | 524.77 | 165.5 | 999 | N/A | N/A |
|  Microgateway-Passthrough-OAuth2 | 4G | 1000 | 1024 | 30 | 0 | 1962.96 | 509.53 | 142.03 | 919 | N/A | N/A |
|  Microgateway-Passthrough-OAuth2 | 4G | 1000 | 1024 | 500 | 0 | 1840.81 | 543.17 | 36.86 | 651 | N/A | N/A |
|  Microgateway-Passthrough-OAuth2 | 4G | 1000 | 1024 | 1000 | 0 | 990.09 | 1008.8 | 23.46 | 1103 | N/A | N/A |
|  Microgateway-Passthrough-OAuth2 | 4G | 1000 | 10240 | 0 | 17.99 | 342.12 | 2831.74 | 9434.75 | 67583 | N/A | N/A |
|  Microgateway-Passthrough-OAuth2 | 4G | 1000 | 10240 | 30 | 99.94 | 49.61 | 19598.26 | 19768.65 | 73727 | N/A | N/A |
|  Microgateway-Passthrough-OAuth2 | 4G | 1000 | 10240 | 500 | 99.47 | 52.68 | 18366.48 | 18046.31 | 73727 | N/A | N/A |
|  Microgateway-Passthrough-OAuth2 | 4G | 1000 | 10240 | 1000 | 99.86 | 48.76 | 19657.78 | 20435.43 | 73727 | N/A | N/A |
