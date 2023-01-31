# Quy định chung
* [Config module ads](https://github.com/AperoVN/AperoModuleAds)
* Luôn sử dụng ID test (config variant appTesst) trong quá trình phát triển
* Chỉ sử dụng ID thật khi đã pass test và build aab (trừ 1 số trường hợp debug cần check ad thật)
* Không để bất kỳ content nào của app đè lên ads, cả loading (Chỉ chấp nhận các loại dialog, popup...có thể bật tắt)
* Ads không được đè lên content app mà ko có scroll
* Không đặt ads ở ngay cạnh trên/dưới thanh điều hướng, vị trí user dễ tương tác
* Không đặt ads tại các màn không cung cấp nội dụng, chức năng của app, như các nội dung nhúng, webview
* Ads không được hiển thị ở ngoài app
  
* Không để ads xuất hiện bất ngờ khiến user dễ click nhầm vào ads , phải có loading giữ khoảng trống cho ads
* Danh sách ID test
~~~
  App Open	ca-app-pub-3940256099942544/3419835294
  Banner	ca-app-pub-3940256099942544/6300978111
  Interstitial	ca-app-pub-3940256099942544/1033173712
  Interstitial Video	ca-app-pub-3940256099942544/8691691433
  Rewarded	ca-app-pub-3940256099942544/5224354917
  Rewarded Interstitial	ca-app-pub-3940256099942544/5354046379
  Native Advanced	ca-app-pub-3940256099942544/2247696110
  Native Advanced Video	ca-app-pub-3940256099942544/1044960115
~~~

* [AdMob policies and restrictions](https://support.google.com/admob/answer/6128543?hl=en)
* [Implementation guidance](https://support.google.com/admob/answer/2936217?hl=en)
# Các loại ad
* [Ad Banner](#ad_banner)
* [Ad Interstitial](#ad_inter)
* [Ad Native](#ad_native)
* [Ad Reward](#ad_reward)
* [Ad Open (ad resume)](#ad_open)


## <a id="ad_banner" ></a>Ad Banner
* Không nên đặt ad banner ngay cạnh nội dung ứng dụng và các yếu tố tương tác
  [Banner ad guidance](https://support.google.com/admob/answer/6128877)
## <a id="ad_inter" ></a>Ad Interstitial 
* Cần preload hợp lí đảm bảo khả năng show cao nhất có thể
* Set null ad interstitial sau khi đã được show và load lại nếu cần thiết
* Chỉ show interstitial khi có hành động chuyển màn từ tương tác của user
  [Interstitial ad guidance](https://support.google.com/admob/answer/6066980)
## <a id="ad_native" ></a>Ad Native
* Luôn có loading trong khi chờ load ad native
* background ad phải khác background content app
*
[AdMob native ads policy compliance checklist](https://support.google.com/admob/answer/6240814)
## <a id="ad_reward" ></a>Ad Reward
* Cần có biểu tượng thông báo AD tại vị trí show ad reward
* Ad reward có thời gian load lâu hơn so với các ad khác => cần preload hợp lí
* Set null sau khi show reward và load lại nếu cần thiết

  [Overview of rewarded ad units](https://support.google.com/admob/answer/7372450?hl=en&ref_topic=7384517)
  [Policies for ads that offer rewards](https://support.google.com/admob/answer/7313578?hl=en)
## <a id="ad_open" ></a>Ad Open (ad resume)
* Luôn disable tại các màn splash, IAP, policy, readme...
* Disable khi open policy, more app, share app, feedback... từ menu, drawer, setting...




