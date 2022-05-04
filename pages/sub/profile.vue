<template>
	<view style="width: 80vw; padding-left: 10vw;">
		<u-row customStyle="margin-bottom: 10px" justify="center">
			<u--image class="user-icon" :showLoading="true" shape="circle" width="200rpx" height="200rpx" :src="userIcon"></u--image>
		</u-row>
		<u-row customStyle="margin-bottom: 10px" justify="center">
			<view class="change-promote">Click to change your portrait</view>
		</u-row>
		
		<u--form>
			<u-form-item class="entry-name">
				<view>Display Name</view>
			</u-form-item>
			<u-form-item>
				<u--input @change="checkForm" border="bottom" placeholder="* Required" v-model="displayName"></u--input>
			</u-form-item>
			
			<u-form-item class="entry-name">
				<view>Gender</view>
			</u-form-item>
			<u-form-item>
				<u-subsection customStyle="margin-top: 20rpx" @change="genderSwap" :list="availableGender" :current="gender"></u-subsection>
			</u-form-item>
			
			<u-form-item class="entry-name">
				<view>Weight</view>
			</u-form-item>
			<u-form-item>
				<u--input @focus="onWeightOrHeightFocus" @blur="onWeightOrHeightBlur" border="bottom" placeholder="Weight" v-model="weight"></u--input>
			</u-form-item>
			
			<u-form-item class="entry-name">
				<view>Height</view>
			</u-form-item>
			<u-form-item>
				<u--input @focus="onWeightOrHeightFocus" @blur="onWeightOrHeightBlur" border="bottom" placeholder="Height" v-model="height"></u--input>
			</u-form-item>
			
			<u-form-item class="confirm-button">
				<u-button class="button" :disabled="disabled" @click="confirm()" text="Update!" type="warning"></u-button>
			</u-form-item>
		</u--form>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				userIcon: "http://127.0.0.1:8080/image/usericon/Ellipse.png",
				displayName: '',
				
				gender: 0,
				weight: "70kg",
				height: "170cm",
				
				prevWeight: '',
				prevHeight: '',
				
				availableGender: [ "female", "male" ],
				disabled: false
			}
		},
		
		onLoad(params) {
			this.displayName = params.name
			this.gender = Number(params.gender)
			this.weight = params.weight + "kg"
			this.height = params.height * 100 + "cm"
		},
		
		methods: {
			checkForm() {
				this.disabled = !this.displayName
			},
			
			genderSwap(index) {
				this.gender = index
			},
			
			onWeightOrHeightFocus() {
				this.prevHeight = this.height
				this.prevWeight = this.weight
			},
			
			onWeightOrHeightBlur() {
				let reg = /[0-9]+(\.[0-9]+)?/
				let res = this.weight.match(reg)
				if(!res)
					this.weight = this.prevWeight
				else if(res[0] == this.weight)
					this.weight += "kg"
				else if(res[0] + "kg" != this.weight)
					this.weight = this.prevWeight
				
				res = this.height.match(reg)
				if(!res)
					this.height = this.prevHeight
				else if(res[0] == this.height)
					this.height += "cm"
				else if(res[0] + "cm" != this.height)
					this.height = this.prevHeight
			},
			
			confirm() {
				let reg = /[0-9]+(\.[0-9]+)?/
				
				uni.$u.http.post(
					'/user/update-profile',
					{ },
					{
						params: {
							displayName: this.displayName,
							weight: this.weight.match(reg)[0],
							height: Number(this.height.match(reg)[0]) / 100 + "",
							gender: this.gender + ""
						},
					}
				).then(
					res => {
						uni.$u.route({ type: "back" })
					}
				)
			}
		}
	}
</script>

<style lang="scss" scoped>
	.confirm-button {
		padding-top: 1vh;
		
		.button {
			font-weight: bold;
			border-radius: 20rpx;
		}
	}
	
	.entry-name {
		height: 40rpx;
		font-size: 35rpx;
		font-weight: bold;
		color: #888;
	}
	
	.user-icon {
		padding-top: 3vh;
		margin: 0 auto;
		display: block;
	}
	
	.change-promote {
		font-size: 26rpx;
		color: #aaa;
	}
</style>
