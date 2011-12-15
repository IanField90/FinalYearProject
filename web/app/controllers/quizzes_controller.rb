class QuizzesController < ApplicationController
  def show
  end

  def new
    @quiz = Quiz.new(params[:quiz])
  end

  def edit
  end

  def create
  end

  def update
  end

  def destroy
  end

end
