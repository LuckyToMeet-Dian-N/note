<template>
  <!-- <div class="wrapper">
    <div class="title"></div>
    <div class="input-wrapper">
      <div class="sub-title"></div>
      <quill-editor class="editor"
                    v-model="messages[index]"
                    ref="myQuillEditor"
                    @blur="onEditorBlur"
                    @focus="onEditorFocus"
                    @ready="onEditorReady">
      </quill-editor>
    </div>
  </div> -->
</template>

<script>
  export default {
    components: {
    },
    props: {
      item: {
        type: Object,
        required: true
      },
      index: {
        type: Number,
        required: true
      },
    },
    data() {
      return {
        messages: [],
      }
    },
   
    methods: {
      onEditorBlur() {
        console.log('blur',this.messages)
      },

      onEditorFocus(){
        console.log('focus',this.messages)
      },

      onEditorReady(){
        console.log('ready',this.messages)
      }
  }
}
</script>

<style lang="scss">

  .wrapper {
    width: 100%;
    display: flex;
    flex-direction: column;
    box-sizing: border-box;

    .title {
      font-size: $font-size-large;
      color: $text-main;
      padding-bottom: px2rem(6);
      line-height: 150%;
    }

    .input-wrapper {
      display: flex;
      flex-direction: row;
      width: 100%;
      margin: px2rem(5) 0;
      box-sizing: border-box;

      .editor{
        width: 100%;
        height: px2rem(200);
      }

      .sub-title {
        font-size: $font-size-normal;
        color: $text-normal;
      }

      .field {
        flex: 1;
        border: 1px solid $border-third;
      }
    }
  }

  div.ql-container.ql-snow{
    height: px2rem(100);
  }

  div.ql-editor.ql-blank{
    height: px2rem(50);
  }

</style>
