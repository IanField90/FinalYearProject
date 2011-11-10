require 'test_helper'

class ProgrammesControllerTest < ActionController::TestCase
  setup do
    @programme = programmes(:one)
  end

  test "should get index" do
    get :index
    assert_response :success
    assert_not_nil assigns(:programmes)
  end

  test "should get new" do
    get :new
    assert_response :success
  end

  test "should create programme" do
    assert_difference('Programme.count') do
      post :create, :programme => @programme.attributes
    end

    assert_redirected_to programme_path(assigns(:programme))
  end

  test "should show programme" do
    get :show, :id => @programme.to_param
    assert_response :success
  end

  test "should get edit" do
    get :edit, :id => @programme.to_param
    assert_response :success
  end

  test "should update programme" do
    put :update, :id => @programme.to_param, :programme => @programme.attributes
    assert_redirected_to programme_path(assigns(:programme))
  end

  test "should destroy programme" do
    assert_difference('Programme.count', -1) do
      delete :destroy, :id => @programme.to_param
    end

    assert_redirected_to programmes_path
  end
end
