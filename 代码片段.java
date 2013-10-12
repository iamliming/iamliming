多维数组
	private byte[] serialize(String data) {
		return serializer.serialize(data);
	}

	private byte[][] serializeMulti(String... keys) {
		byte[][] ret = new byte[keys.length][];

		for (int i = 0; i < ret.length; i++) {
			ret[i] = serializer.serialize(keys[i]);
		}

		return ret;
	}
