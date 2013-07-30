LunaCmd
=======
Admincmdと同じじゃん!と言われたらそれまでだが、個人的に欲しかった機能を追加したAdmincmdみたいなものになる予定。

現在実装済みのコマンド類
======
	/gm [0-2] [Player]		ゲームモードの変更(0-2はそれぞれサバイバル・クリエイティブ・アドベンチャーに対応)
	/fly [Player]			フライモードの変更
	/shell Command ...		シェルコマンドを実行する(Liunx or BSD Only)
	/tp Player				Playerの位置へテレポートする
	/t2p From To			FromのプレイヤーをToにテレポートさせる
	/tphere Player			Playerを今いる位置にテレポートさせる
	/tploc [Player] x y z	Player又は自分をx,y,zの位置にテレポートさせる
	/jail set				今いる位置をJailに設定する
	/jail add Player		PlayerをJailに追加し、又Jailにテレポートさせる
	/jail remove Player		PlayerをJailから開放する
	/world					ワールド管理ヘルプ表示
	/world gen worldname
		([env		(normal|nether|the_end)] |
		 [structure	([on,true]|[off,false])] |
		 [type		(normal, large, flat)])
		 					ワールドを生成する
	/world remove world		ワールドを削除する
	/world go world			ワールドのスポーンにテレポートする
	/world list				ワールドのリストを表示する