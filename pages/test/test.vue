<template>
    <u-tabs lineColor="#000" :list="sortTabs"></u-tabs>
</template>

<script>
	export default {
		data() {
			return {
                sortTabs: [
					{ name: 'Popular' },
					{ name: 'ALL' },
				]
			}
		},
		methods: {
            click(item) {
                console.log('item', item);
            }
		}
	}
</script>