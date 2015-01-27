package com.disturb.BarCode;


public class BarCode128 {
	
	public static final int BINARY_BAR_CODE_LENGTH = 11;		// 바코드 1단위당 길이
	public static final int START_CODE_A = 103;
	public static final int START_CODE_B = 104;
	public static final int START_CODE_C = 105;
	public static final int STOP_CODE = 999;
	public static final int CODE_A = 101;
	public static final int CODE_B = 100;
	public static final int CODE_C = 99;
	public static final int EXTRA_CODE = 9;
	public static final int CHECK_SUM_FACTOR = 103;
	
	
	public static int[] generateBinaryBarCode128FromNumString(String input){
		// 변수 설정
		int barLength = (input.length()/2+6)*11+2;			// 바코드 길이 = 입력문자
		int bar[] = new int[barLength];							// 생성할 이진 바코드 배열		
		int checkNum[] = new int[input.length()/2+4];	// CheckSum 계산용 
		int position = 0;													// 다음 바코드 출력 위치
		int checkSum = 0;
		// 첫번째 값으로 START_CODE_B 를 설정
		checkNum[position] = BarCode128.START_CODE_B;
		setBinaryCodeToPosition(getCodeByIndex(BarCode128.START_CODE_B),bar,position++);
		
		// 두번째 값으로 CODE_C 설정 -> 숫자입력을 위해 2자리씩 입력하는 CODE_C사용
		checkNum[position] = BarCode128.CODE_C;
		setBinaryCodeToPosition(getCodeByIndex(BarCode128.CODE_C),bar,position++);
		
		// 입력을 바코드값으로 입력
		for(int i=0;i<input.length()/2;i++){
			checkNum[position] = Integer.parseInt(input.substring(i*2, i*2+2));
			setBinaryCodeToPosition(getCodeByIndex(checkNum[position]), bar, position++);
		}
		
		// 추가값 입력을 위해 CODE_B 설정
		checkNum[position] = BarCode128.CODE_B;
		setBinaryCodeToPosition(getCodeByIndex(BarCode128.CODE_B),bar,position++);
		
		// 추가값 입력
		checkNum[position] = BarCode128.EXTRA_CODE;
		setBinaryCodeToPosition(getCodeByIndex(BarCode128.EXTRA_CODE),bar,position++);
		
		// Check Sum 입력
		checkSum = getCheckSum(checkNum);		
		setBinaryCodeToPosition(getCodeByIndex(checkSum),bar,position++);
		
		// STOP_CODE 입력		
		setBinaryCodeToPosition(getCodeByIndex(BarCode128.STOP_CODE),bar,position++);
		
		// 종료코드 입력
		bar[position*11] = 0;
		bar[position*11+1] = 0;
		return bar;
	}

	private static int getCheckSum(int[] check){
		int checkSum = check[0];
		for(int i=1;i<check.length;i++){
			checkSum+=check[i]*i;
		}
		return checkSum%BarCode128.CHECK_SUM_FACTOR;
	}
	// 입력 단위 이진 바코드배열을 전체 바코드 코드 배열에 추가 
	private static void setBinaryCodeToPosition(int[] baseCode, int[] fullCode, int position){
		int binaryCode[] = convertDigitCodeToBinaryCode(baseCode);
		for(int i=0;i<binaryCode.length;i++){
			fullCode[i+position*11]=binaryCode[i];
		}
	}
	
	private static int[] convertDigitCodeToBinaryCode(int[] digitCode){
		int binaryCode[] = new int[BarCode128.BINARY_BAR_CODE_LENGTH];
		int index = 0;
		int value = 0;
		for(int i=0;i<digitCode.length;i++){
			value = i%2;
			for(int j=0;j<digitCode[i]; j++){
				binaryCode[index++]=value;
			}
		}		
		return binaryCode;
	}
	
	private static int[] getCodeByIndex(int index){
		int[] code = null;
		switch(index){
		case 0:
			code = new int[]{2,1,2,2,2,2};
			break;
		case 1:
			code = new int[]{2,2,2,1,2,2};
			break;
		case 2:
			code = new int[]{2,2,2,2,2,1};
			break;
		case 3:
			code = new int[]{1,2,1,2,2,3};
			break;
		case 4:
			code = new int[]{1,2,1,3,2,2};
			break;
		case 5:
			code = new int[]{1,3,1,2,2,2};
			break;
		case 6:
			code = new int[]{1,2,2,2,1,3};
			break;
		case 7:
			code = new int[]{1,2,2,3,1,2};
			break;
		case 8:
			code = new int[]{1,3,2,2,1,2};
			break;
		case 9:
			code = new int[]{2,2,1,2,1,3};
			break;
		case 10:
			code = new int[]{2,2,1,3,1,2};
			break;
		case 11:
			code = new int[]{2,3,1,2,1,2};
			break;
		case 12:
			code = new int[]{1,1,2,2,3,2};
			break;
		case 13:
			code = new int[]{1,2,2,1,3,2};
			break;
		case 14:
			code = new int[]{1,2,2,2,3,1};
			break;
		case 15:
			code = new int[]{1,1,3,2,2,2};
			break;
		case 16:
			code = new int[]{1,2,3,1,2,2};
			break;
		case 17:
			code = new int[]{1,2,3,2,2,1};
			break;
		case 18:
			code = new int[]{2,2,3,2,1,1};
			break;
		case 19:
			code = new int[]{2,2,1,1,3,2};
			break;
		case 20:
			code = new int[]{2,2,1,2,3,1};
			break;
		case 21:
			code = new int[]{2,1,3,2,1,2};
			break;
		case 22:
			code = new int[]{2,2,3,1,1,2};
			break;
		case 23:
			code = new int[]{3,1,2,1,3,1};
			break;
		case 24:
			code = new int[]{3,1,1,2,2,2};
			break;
		case 25:
			code = new int[]{3,2,1,1,2,2};
			break;
		case 26:
			code = new int[]{3,2,1,2,2,1};
			break;
		case 27:
			code = new int[]{3,1,2,2,1,2};
			break;
		case 28:
			code = new int[]{3,2,2,1,1,2};
			break;
		case 29:
			code = new int[]{3,2,2,2,1,1};
			break;
		case 30:
			code = new int[]{2,1,2,1,2,3};
			break;
		case 31:
			code = new int[]{2,1,2,3,2,1};
			break;
		case 32:
			code = new int[]{2,3,2,1,2,1};
			break;
		case 33:
			code = new int[]{1,1,1,3,2,3};
			break;
		case 34:
			code = new int[]{1,3,1,1,2,3};
			break;
		case 35:
			code = new int[]{1,3,1,3,2,1};
			break;
		case 36:
			code = new int[]{1,1,2,3,1,3};
			break;
		case 37:
			code = new int[]{1,3,2,1,1,3};
			break;
		case 38:
			code = new int[]{1,3,2,3,1,1};
			break;
		case 39:
			code = new int[]{2,1,1,3,1,3};
			break;
		case 40:
			code = new int[]{2,3,1,1,1,3};
			break;
		case 41:
			code = new int[]{2,3,1,3,1,1};
			break;
		case 42:
			code = new int[]{1,1,2,1,3,3};
			break;
		case 43:
			code = new int[]{1,1,2,3,3,1};
			break;
		case 44:
			code = new int[]{1,3,2,1,3,1};
			break;
		case 45:
			code = new int[]{1,1,3,1,2,3};
			break;
		case 46:
			code = new int[]{1,1,3,3,2,1};
			break;
		case 47:
			code = new int[]{1,3,3,1,2,1};
			break;
		case 48:
			code = new int[]{3,1,3,1,2,1};
			break;
		case 49:
			code = new int[]{2,1,1,3,3,1};
			break;
		case 50:
			code = new int[]{2,3,1,1,3,1};
			break;
		case 51:
			code = new int[]{2,1,3,1,1,3};
			break;
		case 52:
			code = new int[]{2,1,3,3,1,1};
			break;
		case 53:
			code = new int[]{2,1,3,1,3,1};
			break;
		case 54:
			code = new int[]{3,1,1,1,2,3};
			break;
		case 55:
			code = new int[]{3,1,1,3,2,1};
			break;
		case 56:
			code = new int[]{3,3,1,1,2,1};
			break;
		case 57:
			code = new int[]{3,1,2,1,1,3};
			break;
		case 58:
			code = new int[]{3,1,2,3,1,1};
			break;
		case 59:
			code = new int[]{3,3,2,1,1,1};
			break;
		case 60:
			code = new int[]{3,1,4,1,1,1};
			break;
		case 61:
			code = new int[]{2,2,1,4,1,1};
			break;
		case 62:
			code = new int[]{4,3,1,1,1,1};
			break;
		case 63:
			code = new int[]{1,1,1,2,2,4};
			break;
		case 64:
			code = new int[]{1,1,1,4,2,2};
			break;
		case 65:
			code = new int[]{1,2,1,1,2,4};
			break;
		case 66:
			code = new int[]{1,2,1,4,2,1};
			break;
		case 67:
			code = new int[]{1,4,1,1,2,2};
			break;
		case 68:
			code = new int[]{1,4,1,2,2,1};
			break;
		case 69:
			code = new int[]{1,1,2,2,1,4};
			break;
		case 70:
			code = new int[]{1,1,2,4,1,2};
			break;
		case 71:
			code = new int[]{1,2,2,1,1,4};
			break;
		case 72:
			code = new int[]{1,2,2,4,1,1};
			break;
		case 73:
			code = new int[]{1,4,2,1,1,2};
			break;
		case 74:
			code = new int[]{1,4,2,2,1,1};
			break;
		case 75:
			code = new int[]{2,4,1,2,1,1};
			break;
		case 76:
			code = new int[]{2,2,1,1,1,4};
			break;
		case 77:
			code = new int[]{4,1,3,1,1,1};
			break;
		case 78:
			code = new int[]{2,4,1,1,1,2};
			break;
		case 79:
			code = new int[]{1,3,4,1,1,1};
			break;
		case 80:
			code = new int[]{1,1,1,2,4,2};
			break;
		case 81:
			code = new int[]{1,2,1,1,4,2};
			break;
		case 82:
			code = new int[]{1,2,1,2,4,1};
			break;
		case 83:
			code = new int[]{1,1,4,2,1,2};
			break;
		case 84:
			code = new int[]{1,2,4,1,1,2};
			break;
		case 85:
			code = new int[]{1,2,4,2,1,1};
			break;
		case 86:
			code = new int[]{4,1,1,2,1,2};
			break;
		case 87:
			code = new int[]{4,2,1,1,1,2};
			break;
		case 88:
			code = new int[]{4,2,1,2,1,1};
			break;
		case 89:
			code = new int[]{2,1,2,1,4,1};
			break;
		case 90:
			code = new int[]{2,1,4,1,2,1};
			break;
		case 91:
			code = new int[]{4,1,2,1,2,1};
			break;
		case 92:
			code = new int[]{1,1,1,1,4,3};
			break;
		case 93:
			code = new int[]{1,1,1,3,4,1};
			break;
		case 94:
			code = new int[]{1,3,1,1,4,1};
			break;
		case 95:
			code = new int[]{1,1,4,1,1,3};
			break;
		case 96:
			code = new int[]{1,1,4,3,1,1};
			break;
		case 97:
			code = new int[]{4,1,1,1,1,3};
			break;
		case 98:
			code = new int[]{4,1,1,3,1,1};
			break;	
		case CODE_A:		// 101
			code = new int[]{3,1,1,1,4,1};
			break;
		case CODE_B:		// 100
			code = new int[]{1,1,4,1,3,1};
			break;
		case CODE_C:		// 99
			code = new int[]{1,1,3,1,4,1};
			break;
		case START_CODE_A:		// 103
			code = new int[]{2,1,1,4,1,2};
			break;
		case START_CODE_B:		// 104
			code = new int[]{2,1,1,2,1,4};
			break;
		case START_CODE_C:		// 105
			code = new int[]{2,1,1,2,3,2};
			break;
		case STOP_CODE:
			code = new int[]{2,3,3,1,1,1};
			break;
		default:
			code = new int[]{11,0,0,0,0,0};
			break;
		}
		return code;
	}
	
	
}
