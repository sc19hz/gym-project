<template>
	<u-calendar 
        :formatter="formatter"
        :show="show"
        @confirm="confirm"
		ref="calendar"
	>
    </u-calendar>
</template>

<script>
	export default {
		data() {
			return {
				show: true,
			}
		},
		onReady() {
			// 如果需要兼容微信小程序的话，需要用此写法
			this.$refs.calendar.setFormatter(this.formatter)
		},
		methods: {
			confirm(e) {
				console.log(e);
			},
			formatter(day) {
				const d = new Date()
				let month = d.getMonth() + 1
				const date = d.getDate()
				if(day.month == month && day.day == date + 3)
				{
					day.bottomInfo = '有优惠'
					day.dot = true
				}
				return day
		    }
		}
	}
</script>

<style lang="scss" scoped>
	.title{
		color: $u-primary;
		text-align: center;
		padding: 20rpx 0 0 0;
	}
</style>