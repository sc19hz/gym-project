<template>
	<view style="width: 90vw; margin-left: 5vw;">
		<view style="text-align: center; font-size: 50rpx; font-weight: bold; margin-top: 30rpx; margin-bottom: 40rpx; color: #aaa;">
			Top-up records
		</view>
		<view v-for="item, index in topupRecords" :key="index" style="margin-top: 30rpx;">
			<u-row>
				<u-col span="8">
					<u-row>
						<view style="font-size: 34rpx; font-weight: bold;">{{ (item.amount > 0 ? '+' : '') + formatFloat(item.amount) }}£</view>
					</u-row>
					<u-row>
						<view style="font-size: 28rpx; color: #888; margin: 10rpx 0 10rpx 0;">{{ formatDateTime(item.makeTime) }}</view>
					</u-row>
				</u-col>
				<u-col span="4">
					<view v-if="item.paid > 0"
						style="color: #ff9900; font-size: 26rpx; text-align: right;"
					>Paid {{  item.paid }}£</view>
					<view v-else style="color: #10a5f9; font-size: 26rpx; text-align: right;">Complete</view>
				</u-col>
			</u-row>
			<u-divider class="divisor"></u-divider>
		</view>
		
		<block v-if="topupRecords.length==0">
			<view style="color: #888; text-align: center;">
				No record...
			</view>
			<u-divider class="divisor" customStyle="width: 20%; margin: 0 auto;"></u-divider>
		</block>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				topupRecords: [ ]
			}
		},
		
		async onLoad() {
			this.topupRecords = await uni.$u.http.post("/top-up-records");
			console.log("get records: ", this.topupRecords);
		},
		
		methods: {
			formatFloat(value) {
				let v = value + '';
				return !v.match(/\.[0-9]+/) ? v + '.0' : v;
			},
			
			formatDateTime(time) {
				let date = new Date(time);
				return (date.getMonth() + 1) + "/" + date.getDate() + "/" + date.getFullYear() + " " + this.formatTime(date);
			},
			
			formatTime(time) {
				let hour = time.getHours();
				let min = time.getMinutes();
				return (hour < 10 ? '0' + hour : hour) + ":" + (min < 10 ? '0' + min : min);
			}
		}
	}
</script>

<style lang="scss" scoped>
	.divisor {
		margin: 0;
	}

</style>
