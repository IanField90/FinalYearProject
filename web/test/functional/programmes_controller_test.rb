require 'test_helper'

class ProgrammesControllerTest < ActionController::TestCase
  test "should get show" do
    get :show
    assert_response :success
  end

  test "should get list" do
    get :list
    assert_response :success
  end

  test "should get delete" do
    get :delete
    assert_response :success
  end

  test "should get search" do
    get :search
    assert_response :success
  end

  test "should get create" do
    get :create
    assert_response :success
  end

  test "should get edit" do
    get :edit
    assert_response :success
  end

end
