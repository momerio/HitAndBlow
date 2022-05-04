/**
 * 
 */
package NumberGame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author user
 *
 */
public class Main {

	/**
	 * @param args
	 */
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		// 初期化
		System.out.println("//////////////////// HIT AND BLOW GAME ////////////////////");
		System.out.println("以下の条件に当てはまる数字列を推測するゲーム");
		System.out.println("　hit: 数字も位置も同じ数");
		System.out.println("　blow: 推測した数字列の数字が使われている数");
		System.out.println();

		// 文字入力Scanner
		Scanner scanner = new Scanner(System.in);

		int number_digits = 0;
		while (true) {
			System.out.println(">> 推測する桁数を入力してください(1～9)");
			// 数字の桁数(1～9)
			number_digits = scanner.nextInt();
			if (1 <= number_digits && number_digits <= 9) {
				break;
			} else {
				System.out.println("＊number_digitsの値が不正です。");
				continue;
			}
		}

		// 答えの数字を決定する
		List<String> answer_number_list = new ArrayList<String>();

		// 異なる数を答えとする
		List<String> number_list = new ArrayList<String>(
				Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9"));
		Collections.shuffle(number_list);// ランダムにする
		answer_number_list = number_list.subList(0, number_digits);
//		System.out.println("debug(answer): " + answer_number_list);

		// 試行回数
		int try_number = 1;

		// ゲーム本編部分
		while (true) {
			System.out.println(">> " + number_digits + " 桁の数字を入力してください。");

			// 文字入力
			List<String> input_number_list = new ArrayList<String>();// ユーザ入力リスト
			String input_number = scanner.next().toString();

			// 入力した文字列が数字列かどうかをチェックする
			String regex_number = "^[0-9]+$";
			Pattern pattern_number = Pattern.compile(regex_number);
			Matcher match_number = pattern_number.matcher(input_number);
			boolean is_match_result = match_number.matches(); // 入力した文字列が数字列であるか

			// 文字列の長さを取得
			int input_number_length = input_number.length();
			boolean is_match_length = number_digits == input_number_length;// 入力した文字列と最大文字数が同じかどうか

			if (is_match_result && is_match_length) {// 入力が数字のみであれば
//				System.out.println("あなたの入力した数字は " + input_number + " ですね。");
			} else {
				if (!is_match_length) {
					System.out.println("＊入力桁数が答えの桁数と異なります。");
				}
				if (!is_match_result) {
					System.out.println("＊数字列のみ入力してください。");
				}
				continue;
			}

			// ユーザ入力した数字列をリスト化する
			for (int i = 0; i < input_number_length; i++) {
				input_number_list.add(String.valueOf(input_number.charAt(i)));
			}

			// 入力数字列が同一の数字を含まないかをチェックする
			Set<String> temp_input_number_set = new HashSet<>(input_number_list);
			int temp_input_number_set_length = temp_input_number_set.size();// 要素数
			boolean is_same_number = !(temp_input_number_set_length == number_digits);// 同一数字を含んでいないか
			if (is_same_number) {
				System.out.println("＊異なる数字列入力してください。");
				continue;
			}

//			System.out.println("debug(input_number): " + input_number_list);

			// 評価
			int hit_number = 0; // 位置も数字も同じ
			int blow_number = 0; // 数字のみ同じ
			int temp_blow_number = 0;
			for (int i = 0; i < input_number_length; i++) {
				if (answer_number_list.get(i).equals(input_number_list.get(i))) {
					// hitの計算
					hit_number++;
				}
				if (answer_number_list.contains(input_number_list.get(i))) {
					// blowの計算
					temp_blow_number++;
				}
			}
			// blowの確定
			blow_number = temp_blow_number - hit_number;
			System.out.println("(" + try_number + ")" + "hit: " + hit_number + " & blow: " + blow_number);
			System.out.println();

			// ゲーム終了処理
			if (hit_number == number_digits) {
				// すべてがhitの場合
				System.out.println("ゲームクリアおめでとう！");
				System.out.println("試行回数は" + try_number + "回です。");
				System.out.println();
				break;
			}
			// 試行回数を増やす
			try_number++;
		}

	}

}
