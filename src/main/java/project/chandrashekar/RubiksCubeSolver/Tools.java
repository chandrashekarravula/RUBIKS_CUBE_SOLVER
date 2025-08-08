package project.chandrashekar.RubiksCubeSolver;import java.util.Random;
public class Tools {	public static int verify(String s) {
		int[] count = new int[6];
		try {
			for (int i = 0; i < 54; i++)
				count[Color.valueOf(s.substring(i, i + 1)).ordinal()]++;
		} catch (Exception e) {
			return -1;
		}

		for (int i = 0; i < 6; i++)
			if (count[i] != 9)
				return -1;

		FaceCube fc = new FaceCube(s);
		CubieCube cc = fc.toCubieCube();

		return cc.verify();
	}

	public static String randomCube() {
		CubieCube cc = new CubieCube();
		Random gen = new Random();
		cc.setFlip((short) gen.nextInt(CoordCube.N_FLIP));
		cc.setTwist((short) gen.nextInt(CoordCube.N_TWIST));
		do {
			cc.setURFtoDLB(gen.nextInt(CoordCube.N_URFtoDLB));
			cc.setURtoBR(gen.nextInt(CoordCube.N_URtoBR));
		} while ((cc.edgeParity() ^ cc.cornerParity()) != 0);
		FaceCube fc = cc.toFaceCube();
		return fc.to_String();
	}
}
