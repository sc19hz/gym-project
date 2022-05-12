<template>
	<view>
		<u-picker :show="show" :columns="columns" @confirm="show=false" @cancel="show=false" :closeOnClickOverlay="true" @close="show=false"></u-picker>
		<u-button @click="show = true">打开</u-button>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				show: false,
                columns: [
                    ['中国', '美国', '日本']
                ],
			}
		}
	}
</script>